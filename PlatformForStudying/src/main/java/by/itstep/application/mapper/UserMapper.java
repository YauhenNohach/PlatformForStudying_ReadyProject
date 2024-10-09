package by.itstep.application.mapper;

import by.itstep.application.dto.UserReadDto;
import by.itstep.application.entity.User;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel="spring", unmappedTargetPolicy = IGNORE)
public interface UserMapper {
    UserReadDto toReadDto(User object);
    User toEntity(UserReadDto object);
}