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

/**
 * Реализация сервиса для работы с расписаниями.
 *
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения расписаний.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    /**
     * Создание нового расписания.
     *
     * @param scheduleDto объект расписания, который нужно создать.
     */
    @Override
    public void create(ScheduleDto scheduleDto) {
        log.info("[#createSchedule] is calling");
        Schedule schedule = scheduleMapper.toSchedule(scheduleDto);
        scheduleRepository.save(schedule);
        log.info("[#createSchedule] successfully created");
    }

    /**
     * Получение всех расписаний.
     *
     * @return список всех расписаний.
     */
    @Override
    public Response<List<ScheduleDto>> getAll() {
        log.info("[#getAllSchedules] is calling");
        List<ScheduleDto> schedules = scheduleRepository.findAll().stream()
                .map(scheduleMapper::toScheduleDto)
                .collect(Collectors.toList());
        return new Response<>("All schedules", schedules);
    }

    /**
     * Получение расписания по идентификатору.
     *
     * @param id идентификатор расписания, которое нужно получить.
     * @throws RuntimeException если расписание не найдено.
     * @return объект расписания.
     */
    @Override
    public ScheduleDto getById(Long id) {
        log.info("[#getScheduleById] is calling with ID: {}", id);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
        return scheduleMapper.toScheduleDto(schedule);
    }

    /**
     * Обновление существующего расписания.
     *
     * @param id          идентификатор расписания, которое нужно обновить.
     * @param scheduleDto объект расписания с обновленной информацией.
     * @throws RuntimeException если расписание не найдено.
     * @return ответ о результате операции обновления.
     */
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

    /**
     * Удаление расписания.
     *
     * @param id идентификатор расписания, которое нужно удалить.
     * @throws RuntimeException если расписание не найдено.
     */
    @Override
    public void delete(Long id) {
        log.info("[#deleteSchedule] is calling with ID: {}", id);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        scheduleRepository.delete(schedule);
        log.info("[#deleteSchedule] deleted successfully");
    }
}
