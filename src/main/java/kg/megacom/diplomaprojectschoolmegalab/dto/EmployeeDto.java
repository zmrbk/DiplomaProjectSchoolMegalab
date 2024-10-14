package kg.megacom.diplomaprojectschoolmegalab.dto;

import kg.megacom.diplomaprojectschoolmegalab.enums.Position;
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
    private Position position;

    /**
     * Заработная плата сотрудника.
     */
    private Integer salary;

    /**
     * Уникальный идентификатор пользователя, связанного с сотрудником.
     */
    private Long userId;
}

