package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
