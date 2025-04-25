package esprit.microservice.school;

import esprit.microservice.school.Client.StudentClient;
import esprit.microservice.school.Entites.School;
import esprit.microservice.school.Entites.SchoolResponse;
import esprit.microservice.school.Entites.Student;
import feign.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final StudentClient studentClient;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository, StudentClient studentClient) {
        this.schoolRepository = schoolRepository;
        this.studentClient = studentClient;

    }

    public School save(School school) {
        return schoolRepository.save(school);
    }

    public School findById(Long id) {
        return schoolRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        schoolRepository.deleteById(id);
    }

    public School update(School school) {
        return schoolRepository.save(school);
    }

    public List<School> findAll() {
        return schoolRepository.findAll();
    }

    public SchoolResponse findSchoolsWithStudents(Long schoolId) {
        // Récupérer l'école ou créer une école par défaut
        School school = schoolRepository.findById(schoolId).orElseGet(() -> {
            School defaultSchool = new School();
            defaultSchool.setName("School not found");
            defaultSchool.setEmail("School not found");
            defaultSchool.setPhone("School not found");
            defaultSchool.setAddress("School not found");
            return defaultSchool;
        });

        // Récupérer les étudiants via Feign
        List<Student> students = studentClient.findAllStudentsBySchool(schoolId);

        // Construire la réponse manuellement
        SchoolResponse response = new SchoolResponse();
        response.setName(school.getName());
        response.setEmail(school.getEmail());
        response.setPhone(school.getPhone());
        response.setAddress(school.getAddress());
        response.setStudents(students);

        return response;
    }

    /**
     * Récupère toutes les écoles associées à une université donnée
     */
    public List<School> findSchoolsByUniversityId(Long universityId) {
        return schoolRepository.findByUniversityId(universityId);
    }

}
