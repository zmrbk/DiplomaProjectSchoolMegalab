package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления урока.
 */
@Data
@NoArgsConstructor
public class LessonDto {

    /**
     * Уникальный идентификатор урока.
     */
    private Long id;

    /**
     * Тема урока.
     */
    private String topic;

    /**
     * Домашнее задание для урока.
     */
    private String homework;

    /**
     * Уникальный идентификатор расписания, к которому относится урок.
     */
    private Long scheduleId;

    /**
     * Дата и время создания урока.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
}

