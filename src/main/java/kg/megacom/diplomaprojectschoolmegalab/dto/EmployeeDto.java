package kg.megacom.diplomaprojectschoolmegalab.dto;

import kg.megacom.diplomaprojectschoolmegalab.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления информации о сотруднике.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    /**
     * Уникальный идентификатор сотрудника.
     */
    private Long id;

    /**
     * Должность сотрудника.
     */
    private EmployeeStatus employeeStatus;

    /**
     * Заработная плата сотрудника.
     */
    private Integer salary;

    /**
     * Уникальный идентификатор пользователя, связанного с сотрудником.
     */
    private Long userId;
}

