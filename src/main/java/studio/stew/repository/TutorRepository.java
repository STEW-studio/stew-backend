package studio.stew.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long>, JpaSpecificationExecutor<Tutor> {
    Page<Tutor> findAllByUser(User user, PageRequest pageRequest);
    @Query("SELECT t FROM Tutor t " +
            "LEFT JOIN t.applicationList a " +
            "ON DATE(a.createdAt) = CURRENT_DATE " +
            "GROUP BY t " +
            "ORDER BY COUNT(a) DESC")
    List<Tutor> findTodayTutors(Pageable pageable);
    List<Tutor> findByUser(User user);
    List<Tutor> findBySports_SportsId(Long sportsId);
}
