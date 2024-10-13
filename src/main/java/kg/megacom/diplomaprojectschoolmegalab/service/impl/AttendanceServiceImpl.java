package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.AttendanceDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Attendance;
import kg.megacom.diplomaprojectschoolmegalab.mappers.AttendanceMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.AttendanceRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.AttendanceService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для управления посещаемостью.
 *
 * Этот класс отвечает за операции, связанные с посещаемостью студентов, такие как
 * создание, обновление, удаление и получение записей о посещаемости.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    /**
     * Создает новую запись о посещаемости.
     *
     * @param attendanceDto объект DTO посещаемости, содержащий данные для создания.
     */
    @Override
    public void create(AttendanceDto attendanceDto) {
        log.info("[#createAttendance] is calling");
        Attendance attendance = attendanceMapper.toEntity(attendanceDto);
        attendanceRepository.save(attendance);
        log.info("[#createAttendance] successfully created");
    }

    /**
     * Получает все записи о посещаемости.
     *
     * @return объект Response, содержащий список всех записей о посещаемости.
     */
    @Override
    public Response<List<AttendanceDto>> getAll() {
        log.info("[#getAllAttendances] is calling");
        List<AttendanceDto> attendances = attendanceRepository.findAll().stream()
                .map(attendanceMapper::toDto)
                .collect(Collectors.toList());
        return new Response<>("All attendances", attendances);
    }

    /**
     * Получает запись о посещаемости по идентификатору.
     *
     * @param id идентификатор записи о посещаемости, которую необходимо получить.
     * @return объект DTO найденной записи о посещаемости.
     * @throws RuntimeException если запись о посещаемости с указанным идентификатором не найдена.
     */
    @Override
    public AttendanceDto getById(Long id) {
        log.info("[#getAttendanceById] is calling with ID: {}", id);
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));
        return attendanceMapper.toDto(attendance);
    }

    /**
     * Обновляет существующую запись о посещаемости.
     *
     * @param id идентификатор записи о посещаемости, которую необходимо обновить.
     * @param attendanceDto объект DTO посещаемости, содержащий обновленные данные.
     * @return объект Response с обновленной записью о посещаемости.
     * @throws RuntimeException если запись о посещаемости с указанным идентификатором не найдена.
     */
    @Override
    public Response<AttendanceDto> update(Long id, AttendanceDto attendanceDto) {
        log.info("[#updateAttendance] is calling with ID: {}", id);
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));
        attendance.setAttended(attendanceDto.getAttended());
        attendance.setLesson(attendanceMapper.toLesson(attendanceDto.getLessonId()));
        attendance.setStudent(attendanceMapper.toStudent(attendanceDto.getStudentId()));

        attendanceRepository.save(attendance);
        log.info("[#updateAttendance] successfully updated");
        return new Response<>("Attendance updated successfully", attendanceMapper.toDto(attendance));
    }

    /**
     * Удаляет запись о посещаемости по идентификатору.
     *
     * @param id идентификатор записи о посещаемости, которую необходимо удалить.
     * @throws RuntimeException если запись о посещаемости с указанным идентификатором не найдена.
     */
    @Override
    public void delete(Long id) {
        log.info("[#deleteAttendance] is calling with ID: {}", id);
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));
        attendanceRepository.delete(attendance);
        log.info("[#deleteAttendance] deleted successfully");
    }
}