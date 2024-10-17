package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления информации о родителе.
 */
@Data
@RequiredArgsConstructor
public class ParentDto {

    /**
     * Уникальный идентификатор родителя.
     */
    private Long id;

    /**
     * Уникальный идентификатор пользователя, связанного с родителем.
     */
    private Long userId;
    private String status;
}
