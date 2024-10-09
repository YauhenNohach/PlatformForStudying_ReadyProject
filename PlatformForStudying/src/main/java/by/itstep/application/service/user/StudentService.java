package by.itstep.application.service.user;

import by.itstep.application.entity.Student;
import by.itstep.application.repository.StudentRepository;
import by.itstep.application.rest.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {
    private final StudentRepository studentRepository;
    public void registerStudent(Student newStudent) {
        studentRepository.save(newStudent);
    }

    public List<StudentDto> getAllStudentsDto() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::mapToStudentDto)
                .collect(Collectors.toList());
    }

    private StudentDto mapToStudentDto(Student student) {
        return new StudentDto(student.getId(),
                student.getUser().getFirstname(),
                student.getUser().getLastname());
    }
}
