package kg.megacom.diplomaprojectschoolmegalab.dto;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для запроса на вход в систему.
 */
@Data
public class SignInRequest {

    /**
     * Имя пользователя, используемое для аутентификации.
     */
    private String username;

    /**
     * Пароль пользователя для аутентификации.
     */
    private String password;
}
