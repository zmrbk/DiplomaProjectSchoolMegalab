package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO (Data Transfer Object) для представления информации о пользователе.
 */
@Data
public class UserDto {

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long id;

    /**
     * Имя пользователя для входа в систему.
     */
    private String username;

    /**
     * Имя пользователя.
     */
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    private String lastName;

    /**
     * Отчество пользователя.
     */
    private String middleName;

    /**
     * Номер телефона пользователя.
     */
    private String phone;

    /**
     * Электронная почта пользователя.
     */
    private String email;

    /**
     * Список ролей, присвоенных пользователю.
     */
    private List<String> roles;

    /**
     * Дата и время создания учетной записи пользователя.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    /**
     * Статус активности пользователя (true - активен, false - неактивен).
     */
    private boolean isActive;
}

