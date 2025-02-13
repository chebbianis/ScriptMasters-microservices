package esprit.microservice.school;

import esprit.microservice.school.Entites.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {

}
