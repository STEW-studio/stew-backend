package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Portfolio;
import studio.stew.domain.Tutor;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByTutor(Tutor tutor);
}
