package by.itstep.application.rest.controller;

import by.itstep.application.entity.Assignment;
import by.itstep.application.entity.User;
import by.itstep.application.service.assignment.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentRestController {
    private final AssignmentService assignmentService;

    @GetMapping("get")
    public List<Assignment> getAssignments(@AuthenticationPrincipal User user){
        return assignmentService.getAssignments(user);
    }
}
