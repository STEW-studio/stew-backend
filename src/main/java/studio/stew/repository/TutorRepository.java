package studio.stew.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Page<Tutor> findAllByUser(User user, PageRequest pageRequest);
}
