package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления сообщения.
 */
@Data
public class MessageDto {

    /**
     * Уникальный идентификатор сообщения.
     */
    private Long id;

    /**
     * Текст сообщения.
     */
    private String message;

    /**
     * Дата и время создания сообщения.
     * Формат: "дд-ММ-гггг ЧЧ:ММ:СС".
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    /**
     * Статус прочтения сообщения (true - прочитано, false - не прочитано).
     */
    private Boolean isRead;

    /**
     * Уникальный идентификатор автора сообщения.
     */
    private Long authorId;

    /**
     * Уникальный идентификатор получателя сообщения.
     */
    private Long receiverId;
}

