package esprit.microservice.university;

import esprit.microservice.university.model.School;
import esprit.microservice.university.model.UniversityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/universities")
@CrossOrigin("*")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    // Create
    @PostMapping("/add")
    public ResponseEntity<University> createUniversity(@RequestBody University university) {
        University savedUniversity = universityService.saveUniversity(university);
        return new ResponseEntity<>(savedUniversity, HttpStatus.CREATED);
    }

    // Read all
    @GetMapping("/all")
    public ResponseEntity<List<University>> getAllUniversities() {
        List<University> universities = universityService.getAllUniversities();
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable Long id) {
        Optional<University> university = universityService.getUniversityById(id);
        return university.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable Long id, @RequestBody University university) {
        Optional<University> existingUniversity = universityService.getUniversityById(id);
        if (existingUniversity.isPresent()) {
            university.setId(id);
            University updatedUniversity = universityService.updateUniversity(university);
            return new ResponseEntity<>(updatedUniversity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        Optional<University> university = universityService.getUniversityById(id);
        if (university.isPresent()) {
            universityService.deleteUniversity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Find by country
    @GetMapping("/country/{country}")
    public ResponseEntity<List<University>> getUniversitiesByCountry(@PathVariable String country) {
        List<University> universities = universityService.getUniversitiesByCountry(country);
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }

    // Find by city
    @GetMapping("/city/{city}")
    public ResponseEntity<List<University>> getUniversitiesByCity(@PathVariable String city) {
        List<University> universities = universityService.getUniversitiesByCity(city);
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }

    // Search by name
    @GetMapping("/search")
    public ResponseEntity<List<University>> searchUniversitiesByName(@RequestParam String name) {
        List<University> universities = universityService.searchUniversitiesByName(name);
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }

    // Nouveaux endpoints pour la gestion des écoles

    /**
     * Récupère une université avec toutes ses écoles associées
     */
    @GetMapping("/{id}/with-schools")
    public ResponseEntity<UniversityResponse> getUniversityWithSchools(@PathVariable Long id) {
        UniversityResponse response = universityService.getUniversityWithSchools(id);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Ajoute une école à une université
     */
    @PostMapping("/{id}/schools")
    public ResponseEntity<School> addSchoolToUniversity(@PathVariable Long id, @RequestBody School school) {
        School savedSchool = universityService.addSchoolToUniversity(id, school);
        if (savedSchool != null) {
            return new ResponseEntity<>(savedSchool, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Récupère toutes les écoles d'une université
     */
    @GetMapping("/{id}/schools")
    public ResponseEntity<List<School>> getSchoolsByUniversityId(@PathVariable Long id) {
        Optional<University> university = universityService.getUniversityById(id);
        if (university.isPresent()) {
            List<School> schools = universityService.getSchoolsByUniversityId(id);
            return new ResponseEntity<>(schools, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
