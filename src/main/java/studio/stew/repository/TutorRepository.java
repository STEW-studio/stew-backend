package studio.stew.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findByUser(User user);
    Page<Tutor> findByUser(User user, Pageable pageable);
    List<Tutor> findTutorsWithApplicationsByUser(Long userId);
}
