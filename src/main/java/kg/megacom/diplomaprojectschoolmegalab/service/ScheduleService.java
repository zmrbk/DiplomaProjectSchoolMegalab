package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {
    void create(ScheduleDto scheduleDto);
    Response<List<ScheduleDto>> getAll();
    ScheduleDto getById(Long id);
    Response<ScheduleDto> update(Long id, ScheduleDto scheduleDto);
    void delete(Long id);
}
