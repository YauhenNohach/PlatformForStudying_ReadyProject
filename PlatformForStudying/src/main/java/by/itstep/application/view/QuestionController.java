package by.itstep.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {

    @GetMapping("/questions")
    public String showQuestionsPage(@RequestParam("testId") Integer testId, Model model) {
        model.addAttribute("testId", testId);
        return "questions";
    }
}
