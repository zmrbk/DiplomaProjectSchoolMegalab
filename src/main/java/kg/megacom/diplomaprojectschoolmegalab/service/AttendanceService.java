package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.AttendanceDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface AttendanceService {
    void create(AttendanceDto attendanceDto);
    Response<AttendanceDto> update(Long id, AttendanceDto attendanceDto);
    Response<List<AttendanceDto>> getAll();
    AttendanceDto getById(Long id);
    void delete(Long id);
}
