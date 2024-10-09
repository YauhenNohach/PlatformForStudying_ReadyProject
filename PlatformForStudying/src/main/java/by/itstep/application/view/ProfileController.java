package by.itstep.application.view;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String showProfilePage() {
        return "profile";
    }
}
