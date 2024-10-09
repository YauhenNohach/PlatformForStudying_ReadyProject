package by.itstep.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String showTestPage() {
        return "test";
    }

    @GetMapping("/tests")
    public String showTestsPageForTeacher() {
        return "tests";
    }

    @GetMapping("/tests-students")
    public String showPageWithTestsForStudents() {
        return "tests-students";
    }
}
