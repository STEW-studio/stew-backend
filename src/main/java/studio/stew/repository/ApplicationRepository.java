package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
