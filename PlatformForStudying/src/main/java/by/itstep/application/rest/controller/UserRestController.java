package by.itstep.application.rest.controller;

import by.itstep.application.dto.UserFilter;
import by.itstep.application.dto.UserReadDto;
import by.itstep.application.rest.response.PageResponse;
import by.itstep.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;
//    @GetMapping
//    public PageResponse<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
//        Page<UserReadDto> page = userService.findAll(filter, pageable);
//        return PageResponse.of(page);
//    }


}
