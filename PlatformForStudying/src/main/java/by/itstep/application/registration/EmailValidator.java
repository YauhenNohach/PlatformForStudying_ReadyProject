package by.itstep.application.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidator implements Predicate<String> {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    @Override
    public boolean test(String s) {
        return s.matches(EMAIL_REGEX);
    }
}
