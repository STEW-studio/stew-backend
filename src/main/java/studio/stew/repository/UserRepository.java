package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
