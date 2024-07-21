package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
}
