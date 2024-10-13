package kg.megacom.diplomaprojectschoolmegalab.dto;

import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CharterDto {
    private Long id;
    private String title;
    private String description;
    private Long employeeId;
    private LocalDateTime creationDate = LocalDateTime.now();
}
