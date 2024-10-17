package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления расписания уроков.
 */
@Data
@NoArgsConstructor
public class ScheduleDto {

    /**
     * Уникальный идентификатор расписания.
     */
    private Long id;

    /**
     * День недели, когда проходит урок.
     */
    private String dayOfWeek;

    /**
     * Четверть, к которой относится расписание.
     */
    private Short quarter;

    /**
     * Время, в которое начинается урок.
     */
    private String dueTime;

    /**
     * Год, в котором проходит урок.
     */
    private String year;

    /**
     * Идентификатор предмета, связанного с расписанием.
     */
    private Long subjectId;

    /**
     * Идентификатор учителя, который проводит урок.
     */
    private Long teacherId;

    /**
     * Идентификатор класса, для которого составлено расписание.
     */
    private Long classId;

    /**
     * Статус утверждения расписания (true - утверждено, false - не утверждено).
     */
    private Boolean isApprove;
}

