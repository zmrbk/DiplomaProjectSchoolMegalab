package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Parent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParentMapper {
    public ParentDto toParentDto(Parent parent) {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(parent.getId());
        parentDto.setUserId(parent.getUser().getId());
        return parentDto;
    }

    public List<ParentDto> toParentDtoList(List<Parent> all) {
        return all.stream()
                .map(this::toParentDto)
                .collect(Collectors.toList());

    }
}
