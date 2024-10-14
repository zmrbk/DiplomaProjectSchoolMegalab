package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для представления информации о студенте.
 */
@Data
@NoArgsConstructor
public class StudentDto {

    /**
     * Уникальный идентификатор студента.
     */
    private Long id;

    /**
     * Дата рождения студента.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;

    /**
     * Идентификатор класса, к которому принадлежит студент.
     */
    private Long classId;

    /**
     * Идентификатор пользователя, связанного со студентом.
     */
    private Long userId;

    /**
     * Идентификатор родителя студента.
     */
    private Long parentId;

    /**
     * Статус родителя студента.
     */
    private String parentStatus;
}

