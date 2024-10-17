package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления информации о классе студента.
 */
@Data
public class StudentClassDto {

    /**
     * Уникальный идентификатор класса.
     */
    private Long id;

    /**
     * Название класса.
     */
    private String classTitle;

    /**
     * Идентификатор сотрудника, связанного с классом.
     */
    private Long employeeId;

    /**
     * Дата и время создания записи о классе.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
}
