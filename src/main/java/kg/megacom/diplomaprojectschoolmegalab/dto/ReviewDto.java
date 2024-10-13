package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления отзыва.
 */
@Data
@NoArgsConstructor
public class ReviewDto {

    /**
     * Уникальный идентификатор отзыва.
     */
    private Long id;

    /**
     * Содержимое отзыва.
     */
    private String review;

    /**
     * Идентификатор студента, к которому относится отзыв.
     */
    private Long studentId;

    /**
     * Идентификатор автора отзыва.
     */
    private Long authorId;

    /**
     * Дата и время создания отзыва.
     */
    private LocalDateTime creationDate;
}
