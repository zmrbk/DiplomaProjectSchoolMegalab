package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс, представляющий сущность Mark (Оценка) в системе.
 * <p>
 * Этот класс содержит информацию об оценке, выставленной ученику
 * за выполнение задания на уроке. Каждая оценка связана с
 * конкретным учеником и уроком.
 * </p>
 *
 * @see Student
 * @see Lesson
 */
@Entity
@Table(name = "marks")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mark {

    /**
     * Уникальный идентификатор оценки.
     * <p>
     * Генерируется автоматически при создании новой оценки.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Значение оценки.
     * <p>
     * Хранит числовое значение оценки, выставленной ученику.
     * Не может быть null.
     * </p>
     */
    @Column(name = "mark", nullable = false)
    private Integer mark;

    /**
     * Связанный ученик.
     * <p>
     * Связь "многие к одному" с сущностью {@link Student}.
     * Указывает на ученика, которому выставлена оценка.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    /**
     * Связанный урок.
     * <p>
     * Связь "многие к одному" с сущностью {@link Lesson}.
     * Указывает на урок, за который выставлена оценка.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id", nullable = false)
    private Lesson lesson;
}

