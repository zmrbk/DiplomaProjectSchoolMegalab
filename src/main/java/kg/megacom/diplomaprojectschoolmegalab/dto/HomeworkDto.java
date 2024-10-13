package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления информации о домашнем задании.
 */
@Data
public class HomeworkDto {

    /**
     * Уникальный идентификатор домашнего задания.
     */
    private Long id;

    /**
     * Статус выполнения домашнего задания: выполнено или нет.
     */
    private Boolean isDone;

    /**
     * Отзыв учителя о домашнем задании.
     */
    private String teacherReview;

    /**
     * Оценка, выставленная за выполнение домашнего задания.
     */
    private Integer mark;

    /**
     * Дата и время создания домашнего задания.
     * Формат: "дд-ММ-гггг ЧЧ:ММ:СС".
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    /**
     * Уникальный идентификатор студента, выполнившего домашнее задание.
     */
    private Long studentId;

    /**
     * Уникальный идентификатор урока, к которому относится домашнее задание.
     */
    private Long lessonId;
}
