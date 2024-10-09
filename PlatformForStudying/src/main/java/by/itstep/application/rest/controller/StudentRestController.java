package by.itstep.application.rest.controller;

import by.itstep.application.rest.dto.StudentDto;
import by.itstep.application.service.user.StudentService;
import by.itstep.application.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentRestController {
    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ApiResponse<List<StudentDto>> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudentsDto();
        return ApiResponse.success(students);
    }
}
