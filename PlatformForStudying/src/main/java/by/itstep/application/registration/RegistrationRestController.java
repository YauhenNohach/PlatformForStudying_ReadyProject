package by.itstep.application.registration;

import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(path = "api/v1/registration", produces = "application/json")
@RequiredArgsConstructor
public class RegistrationRestController {
    private final RegistrationService registrationService;

    @PostMapping()
    public ApiResponse<String> register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
