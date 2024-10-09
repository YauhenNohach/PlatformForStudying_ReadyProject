package by.itstep.application.rest.controller;

import by.itstep.application.entity.Question;
import by.itstep.application.entity.Test;
import by.itstep.application.entity.User;
import by.itstep.application.rest.dto.TestDTO;
import by.itstep.application.service.test.TestService;
import by.itstep.application.rest.controller.request.TestTimeRequest;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestRestController {
    private final TestService testService;

    @PostMapping("/create")
    public ApiResponse<String> createTest(@AuthenticationPrincipal User user,
                                          @RequestBody Set<Question> questions,
                                          @RequestParam String testName) {
        return testService.createTest(user, questions, testName);
    }

    @PostMapping("/time")
    public ApiResponse<String> setTimingsForTest(@RequestBody TestTimeRequest testRequest) {
        return testService.setTimingsForTest(testRequest);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTest(@PathVariable("id") Long id) {
        return testService.getTest(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<TestDTO>> getAllTestsForStudent(@AuthenticationPrincipal User user) {
        Set<TestDTO> testDTOs = testService.getAllTestsForStudent(user);
        return ResponseEntity.ok(testDTOs);
    }

    @PostMapping("/pass/{testId}")
    public ApiResponse<String> passTest(@AuthenticationPrincipal User user,
                                        @RequestBody List<String> userAnswers,
                                        @PathVariable("testId") Long testId) {
        return testService.passTest(user, userAnswers, testId);
    }

    @GetMapping("/allWithDetails")
    public List<Test> getAllTestsWithDetails(@AuthenticationPrincipal User user) {
        return testService.getAllTestsWithDetails(user);
    }

    @PutMapping("/{idTest}")
    public void closeTest(@PathVariable("idTest") Long idTest) {
        testService.closeTest(idTest);
    }

    @DeleteMapping("/delete/{idTest}")
    public ResponseEntity<String> removeTest(@PathVariable("idTest") Long idTest){
        testService.removeTest(idTest);
        return ResponseEntity.ok("Test deleted successfully");
    }

}
