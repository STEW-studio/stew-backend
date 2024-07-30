package studio.stew.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Application;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findAllByUser(User user, PageRequest pageRequest);

    Page<Application> findByTutorInAndCreatedAtBetween(
            List<Long> tutorIds, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    List<Application> findByTutor(Tutor tutor);
}
