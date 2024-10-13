package kg.megacom.diplomaprojectschoolmegalab.dto;

import kg.megacom.diplomaprojectschoolmegalab.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private Position position;
    private Integer salary;
    private Long userId;
}
