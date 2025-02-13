package esprit.microservice.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public Student save(@RequestBody Student student) {
        System.out.println("Received student: " + student.getName() + " " + student.getSchoolId() + " " + student.getClassId());
        return studentService.save(student);
    }

    @GetMapping("/{id}")
    public Student findById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
    }

    @PostMapping("/update")
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    //find all
    @GetMapping("/all")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<Student>> findAllStudents(@PathVariable Long schoolId) {
        return studentService.findAllStudentsBySchool(schoolId);
    }
}
