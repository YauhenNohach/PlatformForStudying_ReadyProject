package by.itstep.application.login;

import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginRestController {
    private final LoginService loginService;

    @PostMapping()
    public ApiResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

}
