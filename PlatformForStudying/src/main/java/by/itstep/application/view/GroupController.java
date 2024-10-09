package by.itstep.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroupController {
        @GetMapping("/groups")
        public String showLoginPage() {
            return "groups";
        }

}
