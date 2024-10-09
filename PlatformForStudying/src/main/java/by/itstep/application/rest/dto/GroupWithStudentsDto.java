package by.itstep.application.rest.dto;

import by.itstep.application.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupWithStudentsDto {
    private Group group;
    private Set<StudentDto> students;
}
