package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentClassDto {
    private Long id;
    private String classTitle;
    private Long employeeId;
    private LocalDateTime creationDate;
}
