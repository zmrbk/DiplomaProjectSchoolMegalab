package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления задания.
 */
@Data
public class AssignmentDto {

    /**
     * Уникальный идентификатор задания.
     */
    private Long id;

    /**
     * Описание задания.
     */
    private String assignment;

    /**
     * Дата и время создания задания.
     * Формат: "дд-ММ-гггг ЧЧ:ММ:СС".
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    /**
     * Статус выполнения задания.
     * true, если задание выполнено; false в противном случае.
     */
    private Boolean isDone;

    /**
     * Уникальный идентификатор автора задания.
     */
    private Long authorId;

    /**
     * Уникальный идентификатор получателя задания.
     */
    private Long receiverId;
}
