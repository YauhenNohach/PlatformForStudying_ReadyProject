package by.itstep.application.service.group;

import by.itstep.application.entity.Group;
import by.itstep.application.entity.Student;
import by.itstep.application.entity.Teacher;
import by.itstep.application.entity.User;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(MockitoExtension.class)
@Sql({
        "classpath:sql/data.sql"
})
public class GroupServiceTest {
    @Mock
    private GetEntity getEntity;
    @InjectMocks
    private GroupService groupService;

    @Test
    public void createGroup_Success() {
        User user = new User();
        user.setId(1L);

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        Group group = new Group();
        group.setName("TestGroup");
        when(getEntity.findGroupByName("TestGroup")).thenReturn(Optional.empty());
        when(getEntity.getTeacherForUser(user)).thenReturn(teacher);
        //when(getEntity.saveGroup(any(Group.class)));
        //when(getEntity.saveTeacher(any(Teacher.class)));
        ApiResponse<String> responseActual = groupService.createGroup(user, "TestGroup");
        ApiResponse<String> responseExpected = ApiResponse.success("Successfully added group");
        assertEquals("Successfully added group", responseExpected, responseActual);

        verify(getEntity, times(1)).saveGroup(any(Group.class));
        verify(getEntity, times(1)).saveTeacher(any(Teacher.class));
    }

    @Test
    public void createGroup_GroupNameAlreadyExists() {
        User user = new User();
        user.setId(1L);
        String existingGroupName = "ExistingGroup";
        when(getEntity.findGroupByName(existingGroupName)).thenReturn(Optional.of(new Group()));

        ApiResponse<String> response = groupService.createGroup(user, existingGroupName);

        assertTrue(response.getErrorMessage().contains("This group name already exists"));

        verify(getEntity, never()).saveGroup(any(Group.class));
        verify(getEntity, never()).saveTeacher(any(Teacher.class));
    }

    @Test
    public void addStudentToGroup_Success() {
        Long groupId = 1L;
        Long studentId = 2L;
        Group group = new Group();
        Student student = new Student();
        when(getEntity.getGroupById(groupId)).thenReturn(group);
        when(getEntity.getStudentById(studentId)).thenReturn(student);

        ApiResponse<String> response = groupService.addStudentToGroup(groupId, studentId);

        assertThat(response.isError()).isFalse();
        assertThat(response.getResult()).isEqualTo("Successfully added the student to the group");

         verify(getEntity, times(1)).saveGroup(group);
         verify(getEntity, times(1)).saveStudent(student);
    }

    @Test
    public void addStudentToGroup_StudentAlreadyAdded() {
        Long groupId = 1L;
        Long studentId = 2L;
        Group group = new Group();
        Student student = new Student();
        group.addStudentsForGroup(student);
        when(getEntity.getGroupById(groupId)).thenReturn(group);
        when(getEntity.getStudentById(studentId)).thenReturn(student);

        ApiResponse<String> response = groupService.addStudentToGroup(groupId, studentId);

        assertThat(response.isError()).isTrue();
        assertThat(response.getErrorMessage()).contains("This student has already been added to the group");

        verify(getEntity, never()).saveGroup(group);
        verify(getEntity, never()).saveStudent(student);

    }
}