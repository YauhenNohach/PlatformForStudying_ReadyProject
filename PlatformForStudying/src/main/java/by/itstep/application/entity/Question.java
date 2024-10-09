package by.itstep.application.entity;

import by.itstep.application.entity.type.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String rightAnswer;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "possible_answers", joinColumns = @JoinColumn(name = "option_question_id"))
    private List<String> possibleAnswers;
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(text, question.text) && Objects.equals(rightAnswer, question.rightAnswer) && Objects.equals(possibleAnswers, question.possibleAnswers) && type == question.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
