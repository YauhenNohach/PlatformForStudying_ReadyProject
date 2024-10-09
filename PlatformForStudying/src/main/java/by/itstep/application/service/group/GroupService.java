package by.itstep.application.service.group;

import by.itstep.application.entity.*;
import by.itstep.application.rest.response.GroupResponse;
import by.itstep.application.rest.dto.GroupWithStudentsDto;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GetEntity getEntity;
    public ApiResponse<String> createGroup(User user, String groupName) {
        ApiResponse<String> response;
        if (getEntity.findGroupByName(groupName).isPresent()) {
            response = ApiResponse.error("This group name already exists. Please choose another name, for example: " + groupName + " + subject name");
        } else {
            try {
                Group group = new Group();
                group.setName(groupName);

                Teacher teacher = getEntity.getTeacherForUser(user);

                teacher.addGroupForTeacher(group);

                getEntity.saveGroup(group);
                getEntity.saveTeacher(teacher);

                response = ApiResponse.success("Successfully added group");
            } catch (Exception e) {
                response = ApiResponse.error("An error occurred while creating the group");
            }
        }

        return response;
    }

    @Transactional
    public ApiResponse<String> addStudentToGroup(Long groupId, Long studentId) {
        var group = getEntity.getGroupById(groupId);

        if (group == null) {
            return ApiResponse.error("Group with id " + groupId + " not found");
        }

        var student = getEntity.getStudentById(studentId);

        if (student == null) {
            return ApiResponse.error("Student with id " + studentId + " not found");
        }

        if (group.getStudents() != null && group.getStudents().contains(student)) {
            return ApiResponse.error("This student has already been added to the group");
        }

        group.addStudentsForGroup(student);

        getEntity.saveGroup(group);
        getEntity.saveStudent(student);
        return ApiResponse.success("Successfully added the student to the group");
    }

    @Transactional(readOnly = true)
    public ApiResponse<GroupWithStudentsDto> getGroupWithStudents(Long groupId) {
      return getEntity.getGroupWithStudents(groupId);
    }

    public ApiResponse<List<GroupResponse>> getAllGroupResponses(User user) {
        try {
            var teacher = getEntity.getTeacherForUser(user);
            List<Group> groups = teacher.getGroups();
            List<GroupResponse> groupResponses = new ArrayList<>();

            for (Group group : groups) {
                GroupResponse groupResponse = new GroupResponse();
                groupResponse.setId(group.getId());
                groupResponse.setName(group.getName());
                groupResponses.add(groupResponse);
            }

            return ApiResponse.success(groupResponses);
        } catch (Exception e) {
            return ApiResponse.error(null);
        }
    }
}
