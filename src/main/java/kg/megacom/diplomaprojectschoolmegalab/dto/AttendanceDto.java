package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;

@Data
public class AttendanceDto {
    private Long id;
    private Boolean attended;
    private Long studentId;
    private Long lessonId;
}

