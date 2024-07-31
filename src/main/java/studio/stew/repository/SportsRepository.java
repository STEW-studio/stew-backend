package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Sports;

import java.util.List;

public interface SportsRepository extends JpaRepository<Sports, Long> {
    List<Sports> findByNameContaining(String kw);
}
