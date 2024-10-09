package by.itstep.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "students_tests", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    @BatchSize(size = 10)
    private Set<Test> tests = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnoreProperties("student")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_assignments", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "assignment_id"))
    private List<Assignment> assignments = new ArrayList<>();

    public void addTest(Test test) {
        tests.add(test);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public void removeTest(Test test){
        tests.remove(test);
    }
    public Test getActiveTest() {
        return assignments.stream()
                .filter(assignment -> assignment.getTest().isTestAccessible())
                .map(Assignment::getTest)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return this.id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
