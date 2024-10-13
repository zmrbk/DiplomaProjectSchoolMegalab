package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;



/**
 * Класс, представляющий расписание занятий в учебном заведении.
 * <p>
 * Этот класс содержит информацию о днях недели, кварталах, времени занятий,
 * а также связях с предметами, преподавателями и классами.
 * </p>
 */
@Entity
@Table(name = "schedules")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    /**
     * Уникальный идентификатор расписания.
     * <p>
     * Генерируется автоматически при создании нового расписания.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * День недели, когда проходит занятие.
     * <p>
     * Обязательное поле, например: "Понедельник", "Вторник" и т.д.
     * </p>
     */
    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

    /**
     * Номер квартала, в котором проходит занятие.
     * <p>
     * Обязательное поле, указывающее на квартал учебного года.
     * </p>
     */
    @Column(name = "quarter", nullable = false)
    private Short quarter;

    /**
     * Время, когда начинается занятие.
     * <p>
     * Обязательное поле, указывающее время в формате "HH:mm".
     * </p>
     */
    @Column(name = "due_time", nullable = false)
    private String dueTime;

    /**
     * Год, когда проходит занятие.
     * <p>
     * Обязательное поле, указывающее на год учебного года.
     * </p>
     */
    @Column(name = "year", nullable = false)
    private String year;

    /**
     * Предмет, который преподавется на занятии.
     * <p>
     * Связь "многие к одному" с сущностью {@link Subject}.
     * Обязательное поле, указывающее на предмет занятия.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;

    /**
     * Преподаватель, ведущий занятие.
     * <p>
     * Связь "многие к одному" с сущностью {@link Employee}.
     * Обязательное поле, указывающее на учителя занятия.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Employee teacher;

    /**
     * Класс, которому предназначено занятие.
     * <p>
     * Связь "многие к одному" с сущностью {@link StudentClass}.
     * Обязательное поле, указывающее на класс.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    private StudentClass studentClass;

    /**
     * Флаг, указывающий на одобрение расписания.
     * <p>
     * Обязательное поле, указывающее, было ли расписание одобрено.
     * </p>
     */
    @Column(name = "is_approve", nullable = false)
    private Boolean isApprove;
}
