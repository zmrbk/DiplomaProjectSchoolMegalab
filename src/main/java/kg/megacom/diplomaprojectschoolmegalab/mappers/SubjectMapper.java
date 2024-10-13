package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.SubjectsDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Subject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Mapper для преобразования между сущностью {@link Subject} и DTO {@link SubjectsDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link Subject} в {@link SubjectsDto}
 * и наоборот, а также для преобразования списков объектов.
 * </p>
 */
@Component
public class SubjectMapper {

    /**
     * Преобразует объект {@link Subject} в объект {@link SubjectsDto}.
     *
     * @param subject Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link SubjectsDto}.
     */
    public SubjectsDto toSubjectDto(Subject subject) {
        SubjectsDto subjectsDto = new SubjectsDto();
        subjectsDto.setId(subject.getId());
        subjectsDto.setTitle(subject.getTitle());
        subjectsDto.setDescription(subject.getDescription());
        return subjectsDto;
    }

    /**
     * Преобразует список объектов {@link Subject} в список объектов {@link SubjectsDto}.
     *
     * @param all Список сущностей, которые нужно преобразовать.
     * @return Список преобразованных объектов {@link SubjectsDto}.
     */
    public List<SubjectsDto> toSubjectsDtoList(List<Subject> all) {
        return all.stream()
                .map(this::toSubjectDto)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует объект {@link SubjectsDto} в объект {@link Subject}.
     *
     * @param subjectsDto DTO, которое нужно преобразовать.
     * @return Преобразованный объект {@link Subject}.
     */
    public Subject toSubject(SubjectsDto subjectsDto) {
        Subject subject = new Subject();
        subject.setTitle(subjectsDto.getTitle());
        subject.setDescription(subjectsDto.getDescription());
        return subject;
    }
}
