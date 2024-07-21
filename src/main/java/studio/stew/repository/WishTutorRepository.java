package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.mapping.WishTutor;

public interface WishTutorRepository extends JpaRepository<WishTutor, Long> {
}
