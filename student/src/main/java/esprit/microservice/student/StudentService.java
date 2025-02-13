package esprit.microservice.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findBySchoolId(Long schoolId) {
        return studentRepository.findBySchoolId(schoolId);
    }

    public List<Student> findByClassId(Long classId) {
        return studentRepository.findByClassId(classId);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public ResponseEntity<List<Student>> findAllStudentsBySchool(Long schoolId) {
        return ResponseEntity.ok(findBySchoolId(schoolId));
    }
}
