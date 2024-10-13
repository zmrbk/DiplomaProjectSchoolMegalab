package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления роли пользователя.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    /**
     * Уникальный идентификатор роли.
     */
    private Long id;

    /**
     * Список имен ролей, назначенных пользователю.
     */
    private List<String> roleNames;

    /**
     * Идентификатор пользователя, которому назначены роли.
     */
    private Long userId;
}
