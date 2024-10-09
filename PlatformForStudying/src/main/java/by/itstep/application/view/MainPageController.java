package by.itstep.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping("/main")
    public String showMainPage() {
        return "index";
    }
}
