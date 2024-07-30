package studio.stew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studio.stew.domain.Review;
import studio.stew.domain.Tutor;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Integer countAllByTutor(Tutor tutor);
    @Query("select SUM(r.score) from Review r where r.tutor.tutorId = :tutorId")
    Float sumAllScoreByTutor(@Param("tutorId") Long tutorId);
    Review findTopByTutorOrderByCreatedAtDesc(Tutor tutor);
}
