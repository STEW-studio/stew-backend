package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
