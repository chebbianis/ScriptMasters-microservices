package esprit.microservice.school;

import esprit.microservice.school.Entites.School;
import esprit.microservice.school.Entites.SchoolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
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

    @PostMapping("/delete")
    public void deleteById(Long id) {
        schoolService.deleteById(id);
    }

    @PostMapping("/{id}")
    public School findById(@PathVariable Long id) {
        return schoolService.findById(id);
    }

    @PostMapping("/all")
    public List<School> findAll() {
        return schoolService.findAll();
    }

    @GetMapping("/students/{schoolId}")
    public SchoolResponse findAllSchools(@PathVariable Long schoolId) {
        return schoolService.findSchoolsWithStudents(schoolId);
    }
}
