package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.LessonDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface LessonService {
    void create(LessonDto lessonDto);
    Response<List<LessonDto>> getAll();
    LessonDto getById(Long id);
    Response<LessonDto> update(Long id, LessonDto lessonDto);
    void delete(Long id);
    List<LessonDto> getLessonsBySubjectId(Long subjectId);
}
