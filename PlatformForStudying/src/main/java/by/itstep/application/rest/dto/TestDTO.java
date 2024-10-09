package by.itstep.application.rest.dto;

import by.itstep.application.entity.Test;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestDTO {
    private Long id;
    private String testName;
    private Boolean access;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private String createdBy;

    public static TestDTO fromTest(Test test) {
        TestDTO testDTO = new TestDTO();
        testDTO.setId(test.getId());
        testDTO.setTestName(test.getTestName());
        testDTO.setAccess(test.isTestAccessible());
        testDTO.setStartTime(test.getStartTime());
        testDTO.setEndTime(test.getEndTime());
        testDTO.setDuration(test.getDuration());
        testDTO.setCreatedBy(test.getCreatedBy());
        return testDTO;
    }
}
