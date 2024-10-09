package by.itstep.application.service.user;

import by.itstep.application.entity.*;
import by.itstep.application.entity.type.StatusType;
import by.itstep.application.rest.dto.StudentDto;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final GetEntity getEntity;

    public void registerTeacher(Teacher teacher) {
        getEntity.saveTeacher(teacher);
    }

    @Transactional
    public ApiResponse<String> assignTestForGroup(User user, Long idTest, Long idGroup) {
        try {
             getEntity.getTeacherForUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("Sorry, you are not a teacher");
        }

        var group = getEntity.getGroupById(idGroup);
        var test = getEntity.getTestById(idTest);

        List<Student> students = group.getStudents();
        if (students.stream().anyMatch(student -> student.getTests().contains(test))) {
            return ApiResponse.error("This test is already assigned to at least one student in the group");
        }
        students.forEach(student -> {
            if (!student.getTests().contains(test)) {
                student.addTest(test);
            }
        });
        getEntity.saveStudents(students);
        return ApiResponse.success("Test added for students in the group " + group.getName());
    }

    public ApiResponse<String> sendResultTest(User user, Integer rating, Long idAssigment) {
        var teacher = getEntity.getTeacherForUser(user);
        var assignment = getEntity.getAssignmentById(idAssigment);
        if (!assignment.getTest().getCreatedBy().equals(teacher.getUser().getFirstname() + " " +
                                                       teacher.getUser().getLastname())) {
            return ApiResponse.error("sorry, but you can't check this test");
        }
        if (assignment.getStatus() == StatusType.CHECKED) {
            return ApiResponse.error("this assigment was check");
        }
        assignment.setStatus(StatusType.CHECKED);
        assignment.setRating(rating);
        getEntity.saveAssignment(assignment);
        return ApiResponse.success("result was send");
    }

    @Transactional(readOnly = true)
    public List<Test> getAllTests() {
        return getEntity.getAllTests();
    }

    @Transactional(readOnly = true)
    public StudentDto findStudentByFirstAndLastName(String firstname, String lastname) {
      var student = getEntity.getStudentByFirstnameAndLastname(firstname, lastname);
     return convertToStudentDto(student);
    }
    public void removeStudentFromGroup(Long studentId, Long groupId) {
        var group = getEntity.getGroupById(groupId);
        var student = getEntity.getStudentById(studentId);
        group.getStudents().remove(student);
        getEntity.saveGroup(group);
    }
    private StudentDto convertToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getUser().getFirstname());
        studentDto.setLastName(student.getUser().getLastname());
        return studentDto;
    }
}

