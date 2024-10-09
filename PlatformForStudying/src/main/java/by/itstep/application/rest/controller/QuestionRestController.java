package by.itstep.application.rest.controller;

import by.itstep.application.entity.Question;
import by.itstep.application.entity.User;
import by.itstep.application.service.question.QuestionService;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    private final GetEntity getEntity;

    @GetMapping("/{testId}")
    public ResponseEntity<ApiResponse<?>> getTest(@PathVariable("testId") Long testId) {
        ApiResponse<Set<Question>> apiResponse;
        try {
            var test = getEntity.getTestById(testId);
            if (test == null) {
                return new ResponseEntity<>(ApiResponse.error("Test not found"), HttpStatus.NOT_FOUND);
            }

            if (!test.isTestAccessible()) {
                return new ResponseEntity<>(ApiResponse.error("Test is not currently accessible"), HttpStatus.OK);
            }

            if (test.getStartTime() == null || test.getEndTime() == null) {
                return new ResponseEntity<>(ApiResponse.error("No timings for the test"), HttpStatus.OK);
            }

            LocalDateTime now = LocalDateTime.now();

            if (now.isBefore(test.getStartTime())) {
                return new ResponseEntity<>(ApiResponse.error("Test is not yet available"), HttpStatus.OK);
            }

            if (now.isAfter(test.getEndTime())) {
                return new ResponseEntity<>(ApiResponse.error("Test is no longer available"), HttpStatus.OK);
            }

            return new ResponseEntity<>(ApiResponse.success(test), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ApiResponse.error("An error occurred while processing the request"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
