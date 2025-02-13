package esprit.microservice.school.Client;

import esprit.microservice.school.Entites.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE", url = "${application.config.student-url}")
public interface StudentClient {

    @GetMapping("/school/{schoolId}")
    List<Student> findAllStudentsBySchool(@PathVariable("schoolId") Long schoolId);

}
