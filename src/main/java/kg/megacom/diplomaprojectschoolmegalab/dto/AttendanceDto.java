package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) для представления информации о посещаемости.
 */
@Data
public class AttendanceDto {

    /**
     * Уникальный идентификатор записи о посещаемости.
     */
    private Long id;

    /**
     * Статус посещаемости.
     * true, если студент присутствовал; false, если отсутствовал.
     */
    private Boolean attended;

    /**
     * Уникальный идентификатор студента.
     */
    private Long studentId;

    /**
     * Уникальный идентификатор урока.
     */
    private Long lessonId;
}

