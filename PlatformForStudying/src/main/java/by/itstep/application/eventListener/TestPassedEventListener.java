package by.itstep.application.eventListener;

import by.itstep.application.email.EmailSender;
import by.itstep.application.entity.Student;
import by.itstep.application.entity.Test;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestPassedEventListener {
    private final EmailSender emailSender;

    public TestPassedEventListener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @EventListener
    @Transactional
    public void handleTestPassedEvent(TestPassedEvent event) {
        Object source = event.getSource();

        if (source instanceof TestPassedEvent) {
            TestPassedEvent testPassedEvent = (TestPassedEvent) source;
            System.out.println(testPassedEvent);
            Student student = testPassedEvent.getStudent();
            System.out.println("student: " + student.getId());
            Test test = testPassedEvent.getTest();
            System.out.println("test: " + test.getId());
            emailSender.send(student.getUser().getEmail(), "Student " + student.getUser().getLastname() + " passed test: " + test.getTestName());
        }
    }
}

