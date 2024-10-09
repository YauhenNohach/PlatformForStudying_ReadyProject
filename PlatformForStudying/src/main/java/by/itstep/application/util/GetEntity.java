package by.itstep.application.util;

import by.itstep.application.entity.*;
import by.itstep.application.repository.*;
import by.itstep.application.rest.dto.GroupWithStudentsDto;
import by.itstep.application.rest.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetEntity {
    private final TestRepository testRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public User getUserById(Long idUser){
        return userRepository.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + idUser));
    }
    public Test getTestById(Long idTest) {
        return testRepository.findById(idTest)
                .orElseThrow(() -> new IllegalArgumentException("Test not found with ID: " + idTest));
    }

    public Teacher getTeacherForUser(User user) {
        return teacherRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Teacher not found for user: " + user.getId()));
    }

    public Group getGroupByName(String groupName) {
        return groupRepository.findByName(groupName)
                .orElseThrow(() -> new IllegalArgumentException("Group with name " + groupName + " not found"));
    }
    @Transactional(readOnly = true)
    public Group getGroupById(Long idGroup) {
        return groupRepository.findById(idGroup)
                .orElseThrow(() -> new IllegalArgumentException("Group with id " + idGroup + " not found"));
    }
    @Transactional(readOnly = true)
    public Student getStudentById(Long idStudent) {
        return studentRepository.findById(idStudent)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + idStudent));
    }
    @Transactional(readOnly = true)
    public Student getStudentForUser(User user) {
        return studentRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Teacher not found for user: " + user.getId()));
    }
    @Transactional(readOnly = true)
    public Student getStudentByFirstnameAndLastname(String firstname, String lastname) {
        return studentRepository.findByFirstNameAndLastName(firstname, lastname)
                .orElseThrow(() -> new IllegalStateException("Student not found: " + firstname + " " + lastname));
    }
    @Transactional(readOnly = true)
    public Assignment getAssignmentById(Long idAssignment) {
        return assignmentRepository.findById(idAssignment)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with ID: " + idAssignment));
    }

    @Transactional(readOnly = true)
    public Student getStudentForUserWithAssignments(User user) {
        return studentRepository.findByUserWithAssignments(user)
                .orElseThrow(() -> new IllegalStateException("Teacher not found for user: " + user.getId()));
    }

    @Transactional(readOnly = true)
    public List<Test> getAllTests() {
        try {
            return testRepository.findAll();
        } catch (DataAccessException e) {
            handleDataAccessException(e);
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<GroupWithStudentsDto> getGroupWithStudents(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        return groupOptional.map(group -> {
            Set<StudentDto> studentDtos = group.getStudents().stream()
                    .map(student -> new StudentDto(student.getId(), student.getUser().getFirstname(), student.getUser().getLastname()))
                    .collect(Collectors.toSet());
            GroupWithStudentsDto resultDto = new GroupWithStudentsDto(group, studentDtos);
            return ApiResponse.success(resultDto);
        }).orElseThrow(() -> new RuntimeException("Group not found for ID: " + groupId));
    }

    public Optional<Group> findGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Optional<Test> findByIdWithQuestions(Long id) {
        return testRepository.findByIdWithQuestions(id);
    }

    @Transactional(readOnly = true)
    public List<Test> findAllTestsByTeacherId(Long userId) {
        return teacherRepository.findAllTestsByTeacherId(userId);
    }


    public void saveTest(Test test) {
        try {
            testRepository.save(test);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
        }
    }

    public void saveTeacher(Teacher teacher) {
        try {
            teacherRepository.save(teacher);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
        }
    }

    public void saveGroup(Group group) {
        try {
            groupRepository.save(group);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
        }
    }

    public void saveStudent(Student student) {
        try {
            studentRepository.save(student);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
        }
    }

    public void saveAssignment(Assignment assignment) {
        try {
            assignmentRepository.save(assignment);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
        }
    }

    @Transactional
    public void saveQuestions(Set<Question> questions) {
        if (questions != null && !questions.isEmpty()) {
            questionRepository.saveAll(questions);
        }
    }

    @Transactional
    public void saveStudents(List<Student> students) {
        try {
            studentRepository.saveAll(students);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
        }
    }

    public void removeTest(Test test) {
        try {
            testRepository.delete(test);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
        }
    }

    private void handleDataAccessException(DataAccessException e) {
        e.printStackTrace();
    }
}