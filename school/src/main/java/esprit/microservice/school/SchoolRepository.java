package esprit.microservice.school;

import esprit.microservice.school.Entites.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findByUniversityId(Long universityId);
}
