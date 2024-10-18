package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления оценки.
 */
@Data
@NoArgsConstructor
public class MarkDto {

    /**
     * Уникальный идентификатор оценки.
     */
    private Long id;

    /**
     * Значение оценки.
     */
    private Integer mark;

    /**
     * Уникальный идентификатор студента, которому поставлена оценка.
     */
    private Long studentId;

    /**
     * Уникальный идентификатор урока, к которому относится оценка.
     */
    private Long lessonId;
}

