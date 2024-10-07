package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.SubjectsDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Subject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubjectMapper {
    public SubjectsDto toSubjectDto(Subject subject) {
        SubjectsDto subjectsDto = new SubjectsDto();
        subjectsDto.setId(subject.getId());
        subjectsDto.setTitle(subject.getTitle());
        subjectsDto.setDescription(subject.getDescription());
        return subjectsDto;
    }

    public List<SubjectsDto> toSubjectsDtoList(List<Subject> all) {
        return all.stream()
                .map(this::toSubjectDto)
                .collect(Collectors.toList());
    }

    public Subject toSubject(SubjectsDto subjectsDto) {
        Subject subject = new Subject();
        subject.setTitle(subjectsDto.getTitle());
        subject.setDescription(subjectsDto.getDescription());
        return subject;
    }
}
