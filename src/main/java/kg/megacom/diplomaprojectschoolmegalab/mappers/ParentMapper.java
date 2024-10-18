package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Parent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper для преобразования между сущностью {@link Parent} и DTO {@link ParentDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link ParentDto} в {@link Parent}
 * и наоборот.
 * </p>
 */
@Component
public class ParentMapper {

    /**
     * Преобразует объект {@link Parent} в объект {@link ParentDto}.
     *
     * @param parent Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link ParentDto}.
     */
    public ParentDto toParentDto(Parent parent) {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(parent.getId());
        parentDto.setUserId(parent.getUser().getId());
        parentDto.setStatus(parent.getStatus());
        return parentDto;
    }

    /**
     * Преобразует список сущностей {@link Parent} в список {@link ParentDto}.
     *
     * @param all Список сущностей {@link Parent}, которые нужно преобразовать.
     * @return Список преобразованных объектов {@link ParentDto}.
     */
    public List<ParentDto> toParentDtoList(List<Parent> all) {
        return all.stream()
                .map(this::toParentDto)
                .collect(Collectors.toList());
    }
}
