package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
