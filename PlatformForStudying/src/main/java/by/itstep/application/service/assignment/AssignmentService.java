package by.itstep.application.service.assignment;

import by.itstep.application.entity.Assignment;
import by.itstep.application.entity.User;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final GetEntity getEntity;
    @Transactional(readOnly = true)
    public List<Assignment> getAssignments(User user) {
        var student = getEntity.getStudentForUserWithAssignments(user);
        return student.getAssignments();
    }
}
