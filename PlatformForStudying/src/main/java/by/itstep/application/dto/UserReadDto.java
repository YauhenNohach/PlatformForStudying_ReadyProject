package by.itstep.application.dto;

public record UserReadDto(Long id, String username,
                          String firstname,
                          String lastname, String email) {
}
