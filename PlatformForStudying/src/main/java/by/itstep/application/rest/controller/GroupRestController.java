package by.itstep.application.rest.controller;

import by.itstep.application.entity.User;
import by.itstep.application.rest.dto.GroupWithStudentsDto;
import by.itstep.application.rest.response.GroupResponse;
import by.itstep.application.service.group.GroupService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupRestController {
    private final GroupService groupService;

    @PostMapping("/create")
    public ApiResponse<String> createGroup(@AuthenticationPrincipal User user, @RequestParam("groupName") String groupName) {
        return groupService.createGroup(user, groupName);
    }

    @PostMapping("/{groupId}/addStudent/{studentId}")
    public ApiResponse<String> addStudentToGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        return groupService.addStudentToGroup(groupId, studentId);
    }
    @GetMapping("/all")
    public ApiResponse<List<GroupResponse>> getAllGroups(@AuthenticationPrincipal User user) {
        return groupService.getAllGroupResponses(user);
    }
    @GetMapping("/{groupId}")
    public ApiResponse<GroupWithStudentsDto> getGroupWithStudents(@PathVariable Long groupId) {
        return groupService.getGroupWithStudents(groupId);
    }
}
