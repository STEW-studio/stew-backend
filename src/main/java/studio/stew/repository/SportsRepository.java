package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Sports;

public interface SportsRepository extends JpaRepository<Sports, Long> {
}
