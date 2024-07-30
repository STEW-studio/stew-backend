package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studio.stew.domain.Portfolio;
import studio.stew.domain.Tutor;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByTutor(Tutor tutor);
    @Query("SELECT p.imgUrl FROM Portfolio p WHERE p.tutor = :tutor")
    List<String> findAllImgUrlByTutor(@Param("tutor") Tutor tutor);
}
