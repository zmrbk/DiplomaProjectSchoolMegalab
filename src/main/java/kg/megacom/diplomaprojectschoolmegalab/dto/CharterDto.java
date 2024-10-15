package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления информации о charter (уставе).
 */
@Data
@RequiredArgsConstructor
public class CharterDto {

    /**
     * Уникальный идентификатор устава.
     */
    private Long id;

    /**
     * Заголовок устава.
     */
    private String title;

    /**
     * Описание устава.
     */
    private String description;

    /**
     * Уникальный идентификатор сотрудника, связанного с уставом.
     */
    private Long employeeId;

    /**
     * Дата и время создания устава.
     * По умолчанию устанавливается на текущее время.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate = LocalDateTime.now();
}

