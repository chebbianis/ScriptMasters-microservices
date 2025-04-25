package esprit.microservice.university;

import esprit.microservice.university.client.SchoolClient;
import esprit.microservice.university.model.School;
import esprit.microservice.university.model.UniversityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final SchoolClient schoolClient;

    @Autowired
    public UniversityService(UniversityRepository universityRepository, SchoolClient schoolClient) {
        this.universityRepository = universityRepository;
        this.schoolClient = schoolClient;
    }

    // Create
    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }

    // Read all
    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    // Read by id
    public Optional<University> getUniversityById(Long id) {
        return universityRepository.findById(id);
    }

    // Update
    public University updateUniversity(University university) {
        return universityRepository.save(university);
    }

    // Delete
    public void deleteUniversity(Long id) {
        universityRepository.deleteById(id);
    }

    // Custom methods
    public List<University> getUniversitiesByCountry(String country) {
        return universityRepository.findByCountry(country);
    }

    public List<University> getUniversitiesByCity(String city) {
        return universityRepository.findByCity(city);
    }

    public List<University> searchUniversitiesByName(String name) {
        return universityRepository.findByNameContaining(name);
    }

    // Méthodes pour la communication avec School

    /**
     * Récupère une université avec toutes ses écoles associées
     */
    public UniversityResponse getUniversityWithSchools(Long universityId) {
        Optional<University> universityOptional = universityRepository.findById(universityId);

        if (universityOptional.isEmpty()) {
            return null;
        }

        University university = universityOptional.get();

        // Créer l'objet de réponse
        UniversityResponse response = new UniversityResponse();
        response.setId(university.getId());
        response.setName(university.getName());
        response.setAddress(university.getAddress());
        response.setCity(university.getCity());
        response.setCountry(university.getCountry());
        response.setWebsite(university.getWebsite());
        response.setEmail(university.getEmail());
        response.setFoundedYear(university.getFoundedYear());
        response.setDescription(university.getDescription());

        // Appeler le microservice School via Feign
        try {
            List<School> schools = schoolClient.findSchoolsByUniversityId(universityId);
            response.setSchools(schools);
        } catch (Exception e) {
            response.setSchools(Collections.emptyList());
        }

        return response;
    }

    /**
     * Ajoute une école associée à une université
     */
    public School addSchoolToUniversity(Long universityId, School school) {
        // Vérifier si l'université existe
        if (!universityRepository.existsById(universityId)) {
            return null;
        }

        // Définir l'ID de l'université dans l'école
        school.setUniversityId(universityId);

        // Enregistrer l'école via le client Feign
        return schoolClient.saveSchool(school);
    }

    /**
     * Récupère toutes les écoles associées à une université
     */
    public List<School> getSchoolsByUniversityId(Long universityId) {
        // Vérifier si l'université existe
        if (!universityRepository.existsById(universityId)) {
            return Collections.emptyList();
        }

        // Appeler le microservice School via Feign
        try {
            return schoolClient.findSchoolsByUniversityId(universityId);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
