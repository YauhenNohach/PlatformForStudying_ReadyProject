package by.itstep.application.service.assignment;

import by.itstep.application.annotation.IntegrationTest;
import by.itstep.application.entity.Assignment;
import by.itstep.application.entity.Student;
import by.itstep.application.entity.User;
import by.itstep.application.util.GetEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Sql({
        "classpath:sql/data.sql"
})
public class AssignmentServiceTest {
    @Mock
    private GetEntity getEntity;
    @InjectMocks
    private AssignmentService assignmentService;
    @Test
    public void getAssignments_Success() {

        User user = new User();
        Student student = Mockito.mock(Student.class);
        Assignment assignment1 = new Assignment();
        Assignment assignment2 = new Assignment();

        when(getEntity.getStudentForUserWithAssignments(user)).thenReturn(student);
        when(student.getAssignments()).thenReturn(Arrays.asList(assignment1, assignment2));

        List<Assignment> assignments = assignmentService.getAssignments(user);

        assertThat(assignments).isNotNull();
        assertThat(assignments).hasSize(2);
        assertThat(assignments).contains(assignment1, assignment2);

        verify(getEntity, times(1)).getStudentForUserWithAssignments(user);
        verify(student, times(1)).getAssignments();
    }

    @Test
    public void getAssignments_NoAssignments() {
        User user = new User();
        Student student = Mockito.mock(Student.class);

        when(getEntity.getStudentForUserWithAssignments(user)).thenReturn(student);
        when(student.getAssignments()).thenReturn(Collections.emptyList());

        List<Assignment> assignments = assignmentService.getAssignments(user);

        assertThat(assignments).isNotNull();
        assertThat(assignments).isEmpty();

        verify(getEntity, times(1)).getStudentForUserWithAssignments(user);
        verify(student, times(1)).getAssignments();
    }

}