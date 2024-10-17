package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.HomeworkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.MarkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface HomeworkService {
    HomeworkDto create(HomeworkDto homeworkDto);
    Response<HomeworkDto> update(Long id, HomeworkDto homeworkDto);
    Response<List<HomeworkDto>> getAll();
    HomeworkDto getById(Long id);
    void delete(Long id);
    List<HomeworkDto> getCompletedHomework(Long classId, Long subjectId);
    MarkDto gradeHomework(Long homeworkId, MarkDto markDto);

}
