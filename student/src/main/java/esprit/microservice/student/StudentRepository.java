package esprit.microservice.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findBySchoolId(Long schoolId);
    List<Student> findByClassId(Long classId);

}
