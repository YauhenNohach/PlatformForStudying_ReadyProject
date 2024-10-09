package by.itstep.application.eventListener;

import by.itstep.application.entity.Student;
import by.itstep.application.entity.Test;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TestPassedEvent extends ApplicationEvent {
    private final Student student;
    private final Test test;

    public TestPassedEvent(Object source, Student student, Test test) {
        super(source);
        this.student = student;
        this.test = test;
    }
}