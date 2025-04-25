package esprit.microservice.school;

import esprit.microservice.school.Entites.School;
import esprit.microservice.school.Entites.SchoolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
@CrossOrigin("*")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/add")
    public School save(@RequestBody School school) {
        return schoolService.save(school);
    }

    @PostMapping("/update")
    public School update(@RequestBody School school) {
        return schoolService.update(school);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        schoolService.deleteById(id);
    }

    @GetMapping("/{id}")
    public School findById(@PathVariable Long id) {
        return schoolService.findById(id);
    }

    @GetMapping("/all")
    public List<School> findAll() {
        return schoolService.findAll();
    }

    @GetMapping("/students/{schoolId}")
    public SchoolResponse findAllSchools(@PathVariable Long schoolId) {
        return schoolService.findSchoolsWithStudents(schoolId);
    }

    /**
     * Récupère toutes les écoles associées à une université donnée
     */
    @GetMapping("/university/{universityId}")
    public ResponseEntity<List<School>> findSchoolsByUniversityId(@PathVariable Long universityId) {
        List<School> schools = schoolService.findSchoolsByUniversityId(universityId);
        return new ResponseEntity<>(schools, HttpStatus.OK);
    }
}
