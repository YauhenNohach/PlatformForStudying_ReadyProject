package by.itstep.application.service.test;

import by.itstep.application.entity.*;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@Sql({
        "classpath:sql/data.sql"
})
class TestServiceTest {
    @Mock
    private GetEntity getEntity;
    @InjectMocks
    private TestService testService;

    @org.junit.jupiter.api.Test
    public void createTest_Success() {
        User user = new User();
        user.setFirstname("tyz");
        user.setLastname("corporation");

        Set<Question> questions = new HashSet<>();
        Question question1 = new Question();
        Question question2 = new Question();

        questions.add(question1);
        questions.add(question2);

        String testName = "Test 1";
        Teacher teacher = Mockito.mock(Teacher.class);

        when(getEntity.getTeacherForUser(user)).thenReturn(teacher);

        ApiResponse<String> responseActual = testService.createTest(user, questions, testName);
        assertThat(responseActual).isNotNull();
        ApiResponse<String> responseExpected = ApiResponse.success("Test saved");
        assertThat(responseActual).isEqualTo(responseExpected);

        verify(getEntity, times(1)).saveQuestions(questions);

        ArgumentCaptor<Test> testCaptor = ArgumentCaptor.forClass(Test.class);

        verify(getEntity, times(1)).saveTest(testCaptor.capture());

        Test savedTest = testCaptor.getValue();

        assertThat(savedTest.getCreatedBy()).isEqualTo("tyz corporation");
        assertThat(savedTest.getTestName()).isEqualTo(testName);
        assertTrue(savedTest.getQuestions().contains(question1));
        assertTrue(savedTest.getQuestions().contains(question2));
        verify(teacher, times(1)).addTestForTeacher(savedTest);
        verify(getEntity, times(1)).saveTeacher(teacher);
    }

    @org.junit.jupiter.api.Test
    public void createTest_EmptyQuestions_failure() {
        User user = new User();
        Set<Question> questions = new HashSet<>();
        String testName = "Test 1";

        ApiResponse<String> responseActual = testService.createTest(user, questions, testName);
        assertThat(responseActual).isNotNull();
        ApiResponse<String> responseExpected = ApiResponse.error("Cannot create a test without questions");
        assertThat(responseActual).isEqualTo(responseExpected);

        //verifyNoInteractions(getEntity);
    }

    @org.junit.jupiter.api.Test
    public void createTest_Exception_Failure() {
        User user = new User();
        Set<Question> questions = new HashSet<>();

        questions.add(new Question());

        String testName ="Test 1";

        Teacher teacher = new Teacher();
        when(getEntity.getTeacherForUser(user)).thenReturn(teacher);

        doThrow(new RuntimeException("Test error")).when(getEntity).saveQuestions(questions);

        ApiResponse<String> responseActual = testService.createTest(user, questions, testName);
        assertThat(responseActual).isNotNull();
        ApiResponse<String> responseExpected = ApiResponse.error("An error occurred while creating the test");

        assertThat(responseActual).isEqualTo(responseExpected);

        verify(getEntity, times(1)).saveQuestions(questions);
    }
}