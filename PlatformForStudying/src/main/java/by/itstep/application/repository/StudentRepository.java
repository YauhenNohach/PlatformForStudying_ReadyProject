package by.itstep.application.repository;

import by.itstep.application.entity.Student;

import by.itstep.application.entity.Teacher;
import by.itstep.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);
    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.assignments WHERE s.user = :user")
    Optional<Student> findByUserWithAssignments(@Param("user") User user);

    @Query("SELECT s FROM Student s WHERE s.user.firstname = :firstname AND s.user.lastname = :lastname")

    Optional<Student> findByFirstNameAndLastName(@Param("firstname") String firstname, @Param("lastname") String lastname);

//    @Query("SELECT DISTINCT t FROM Teacher t WHERE CONCAT(t.firstname, ' ', t.lastname) = :createdBy")
//    Teacher findTeacherByTestCreatedBy(@Param("createdBy") String createdBy);
}
