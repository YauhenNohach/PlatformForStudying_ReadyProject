package by.itstep.application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String testName;
    private Boolean access = true;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private String createdBy;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tests_questions", joinColumns = @JoinColumn(name = "test_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions;

    public boolean isTestAccessible() {
        LocalDateTime now = LocalDateTime.now();
        return access && (startTime == null || now.isAfter(startTime))
               && (endTime == null || now.isBefore(endTime));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(id, test.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
