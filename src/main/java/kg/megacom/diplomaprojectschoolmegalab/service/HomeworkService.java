package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.HomeworkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface HomeworkService {
    void create(HomeworkDto homeworkDto);
    Response<HomeworkDto> update(Long id, HomeworkDto homeworkDto);
    Response<List<HomeworkDto>> getAll();
    HomeworkDto getById(Long id);
    void delete(Long id);
}
