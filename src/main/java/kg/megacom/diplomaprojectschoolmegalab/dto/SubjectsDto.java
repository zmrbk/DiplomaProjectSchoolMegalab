package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) для представления информации о предмете.
 */
@Data
public class SubjectsDto {

    /**
     * Уникальный идентификатор предмета.
     */
    private Long id;

    /**
     * Название предмета.
     */
    private String title;

    /**
     * Описание предмета.
     */
    private String description;
}

