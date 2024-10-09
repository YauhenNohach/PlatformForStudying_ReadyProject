package by.itstep.application.repository;

import by.itstep.application.entity.Group;
import by.itstep.application.entity.Teacher;
import by.itstep.application.entity.Test;
import by.itstep.application.entity.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import static by.itstep.application.entity.QTeacher.teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>,
        QuerydslPredicateExecutor<Teacher> {
    Optional<Teacher> findByUser(User user);
    @Query("SELECT DISTINCT g FROM Teacher t JOIN t.groups g WHERE t.id = :teacherId")
    List<Group> findGroupsByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT DISTINCT t.tests FROM Teacher t WHERE t.user.id = :userId")
    List<Test> findAllTestsByTeacherId(@Param("userId") Long userId);

    Optional<Teacher> findOne(Predicate predicate);

    default Optional<Teacher> findByCreatedBy(String createdBy) {
        return findOne(teacher.user.firstname.concat(" ").concat(teacher.user.lastname).eq(createdBy));
    }
}
