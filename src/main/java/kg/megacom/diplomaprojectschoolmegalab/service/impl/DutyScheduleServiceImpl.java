package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.DutyScheduleDto;
import kg.megacom.diplomaprojectschoolmegalab.service.DutyScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DutyScheduleServiceImpl implements DutyScheduleService {

    @Override
    public DutyScheduleDto createDutySchedule(List<String> dutyList) {
        // Формируем строку из списка дежурных
        String schedule = String.join(", ", dutyList);

        // Создаем объект графика дежурств
        DutyScheduleDto dutyScheduleDto = new DutyScheduleDto();
        dutyScheduleDto.setSchedule(schedule);
        dutyScheduleDto.setCreationDate(LocalDateTime.now());

        // Логика сохранения графика в базу данных (если нужно)

        return dutyScheduleDto;
    }
}