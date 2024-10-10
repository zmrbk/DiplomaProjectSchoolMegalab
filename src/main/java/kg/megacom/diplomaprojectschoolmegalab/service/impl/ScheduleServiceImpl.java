package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ScheduleDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Schedule;
import kg.megacom.diplomaprojectschoolmegalab.mappers.ScheduleMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.ScheduleRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.ScheduleService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public void create(ScheduleDto scheduleDto) {
        log.info("[#createSchedule] is calling");
        Schedule schedule = scheduleMapper.toSchedule(scheduleDto);
        scheduleRepository.save(schedule);
        log.info("[#createSchedule] successfully created");
    }

    @Override
    public Response<List<ScheduleDto>> getAll() {
        log.info("[#getAllSchedules] is calling");
        List<ScheduleDto> schedules = scheduleRepository.findAll().stream()
                .map(scheduleMapper::toScheduleDto)
                .collect(Collectors.toList());
        return new Response<>("All schedules", schedules);
    }

    @Override
    public ScheduleDto getById(Long id) {
        log.info("[#getScheduleById] is calling with ID: {}", id);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
        return scheduleMapper.toScheduleDto(schedule);
    }

    @Override
    public Response<ScheduleDto> update(Long id, ScheduleDto scheduleDto) {
        log.info("[#updateSchedule] is calling with ID: {}", id);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
        schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        schedule.setQuarter(scheduleDto.getQuarter());
        schedule.setDueTime(scheduleDto.getDueTime());
        schedule.setYear(scheduleDto.getYear());
        schedule.setSubject(scheduleMapper.toSubject(scheduleDto.getSubjectId()));
        schedule.setTeacher(scheduleMapper.toTeacher(scheduleDto.getTeacherId()));
        schedule.setStudentClass(scheduleMapper.toStudentClass(scheduleDto.getClassId()));
        schedule.setIsApprove(scheduleDto.getIsApprove());
        scheduleRepository.save(schedule);
        return new Response<>("Schedule updated successfully", scheduleMapper.toScheduleDto(schedule));
    }

    @Override
    public void delete(Long id) {
        log.info("[#deleteSchedule] is calling with ID: {}", id);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
        scheduleRepository.delete(schedule);
        log.info("[#deleteSchedule] deleted successfully");
    }
}

