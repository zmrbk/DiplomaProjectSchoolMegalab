package kg.megacom.diplomaprojectschoolmegalab.service;
import kg.megacom.diplomaprojectschoolmegalab.dto.DutyScheduleDto;

import java.util.List;

public interface DutyScheduleService {
    DutyScheduleDto createDutySchedule(List<String> dutyList);
}