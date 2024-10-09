package by.itstep.application.entity;

import by.itstep.application.entity.type.StatusType;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "assignments_answers", joinColumns = @JoinColumn(name = "answer_id"))
    private List<String> userAnswers;
    @Enumerated(value = EnumType.STRING)
    private StatusType status;
    private Integer rating;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id) && Objects.equals(test, that.test) && Objects.equals(userAnswers, that.userAnswers) && status == that.status && Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
