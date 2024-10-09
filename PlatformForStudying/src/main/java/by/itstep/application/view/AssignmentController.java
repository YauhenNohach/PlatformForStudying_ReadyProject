package by.itstep.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssignmentController {
    @GetMapping("/assignments")
    public String showPageAssignments() {
        return "assignments";
    }
}
