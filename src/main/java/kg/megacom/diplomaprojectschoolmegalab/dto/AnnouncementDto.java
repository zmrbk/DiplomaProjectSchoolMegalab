package kg.megacom.diplomaprojectschoolmegalab.dto;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления объявления.
 */
@Data
public class AnnouncementDto {

    /**
     * Уникальный идентификатор объявления.
     */
    private Long id;

    /**
     * Заголовок объявления.
     */
    private String title;

    /**
     * Описание объявления.
     */
    private String description;

    /**
     * Автор объявления.
     */
    private String author;

    /**
     * Дата и время создания объявления.
     */
    private LocalDateTime creationDate;
}
