package esprit.microservice.university.client;

import esprit.microservice.university.model.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "SCHOOL-SERVICE", url = "${application.config.school-url}")
public interface SchoolClient {

    @GetMapping("/all")
    List<School> findAllSchools();

    @GetMapping("/{id}")
    School findSchoolById(@PathVariable("id") Long id);

    @GetMapping("/university/{universityId}")
    List<School> findSchoolsByUniversityId(@PathVariable("universityId") Long universityId);

    @PostMapping("/add")
    School saveSchool(@RequestBody School school);
}