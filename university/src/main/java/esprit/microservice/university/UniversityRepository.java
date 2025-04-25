package esprit.microservice.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findByCountry(String country);

    List<University> findByCity(String city);

    List<University> findByNameContaining(String name);
}
