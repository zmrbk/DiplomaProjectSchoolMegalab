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

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public void create(AttendanceDto attendanceDto) {
        log.info("[#createAttendance] is calling");
        Attendance attendance = attendanceMapper.toEntity(attendanceDto);
        attendanceRepository.save(attendance);
        log.info("[#createAttendance] successfully created");
    }

    @Override
    public Response<List<AttendanceDto>> getAll() {
        log.info("[#getAllAttendances] is calling");
        List<AttendanceDto> attendances = attendanceRepository.findAll().stream()
                .map(attendanceMapper::toDto)
                .collect(Collectors.toList());
        return new Response<>("All attendances", attendances);
    }

    @Override
    public AttendanceDto getById(Long id) {
        log.info("[#getAttendanceById] is calling with ID: {}", id);
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));
        return attendanceMapper.toDto(attendance);
    }

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

    @Override
    public void delete(Long id) {
        log.info("[#deleteAttendance] is calling with ID: {}", id);
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));
        attendanceRepository.delete(attendance);
        log.info("[#deleteAttendance] deleted successfully");
    }
}
