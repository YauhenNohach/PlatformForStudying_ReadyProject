package by.itstep.application.service.test;

import by.itstep.application.entity.Assignment;

import java.util.ArrayList;
import java.util.List;

public class CheckAnswerFromTest {
    public static int calculateScore(double overallSimilarity) {
        double scaledScore = overallSimilarity * 9 + 1;

        return (int) Math.round(scaledScore);
    }

    public static int checkTest(Assignment assignment) {
        var test = assignment.getTest();
        var userAnswers = assignment.getUserAnswers();
        List<String> rightAnswers = new ArrayList<>();
        test.getQuestions().forEach(question -> rightAnswers.add(question.getRightAnswer()));
        double overallSimilarity = calculateOverallSimilarity(userAnswers, rightAnswers);

        return calculateScore(overallSimilarity);
    }

    public static double calculateOverallSimilarity(List<String> userAnswers, List<String> rightAnswers) {
        double countRightAnswer = 0;
        for (int i = 0; i < userAnswers.size(); i++) {
            if (rightAnswers.get(i).equals(userAnswers.get(i))) {
                countRightAnswer++;
            }
        }

        return countRightAnswer / rightAnswers.size();
    }
}