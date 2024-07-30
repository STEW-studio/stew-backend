package studio.stew.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Application;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findByUserAndCreatedAtBetween(
            Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<Application> findByTutorInAndCreatedAtBetween(
            List<Long> tutorIds, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
