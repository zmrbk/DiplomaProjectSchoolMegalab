package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * Класс, представляющий сущность Homework (Домашнее задание) в системе.
 * <p>
 * Этот класс содержит информацию о домашнем задании,
 * включая статус выполнения, оценку и отзыв преподавателя.
 * Связь с учениками и уроками осуществляется через соответствующие поля.
 * </p>
 *
 * @see Student
 * @see Lesson
 */
@Entity
@Table(name = "homeworks")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Homework {

    /**
     * Уникальный идентификатор домашнего задания.
     * <p>
     * Генерируется автоматически при создании нового задания.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Статус выполнения домашнего задания.
     * <p>
     * Значение по умолчанию - false. Указывает, выполнено ли задание учеником.
     * </p>
     */
    @Column(name = "is_done", nullable = false)
    private Boolean isDone = false;

    /**
     * Отзыв преподавателя о выполненном задании.
     * <p>
     * Хранит текстовый комментарий, оставленный учителем по поводу
     * выполнения домашнего задания.
     * </p>
     */
    @Column(name = "teacher_review")
    private String teacherReview;

    /**
     * Оценка, полученная за домашнее задание.
     * <p>
     * Представляет собой целочисленное значение, указывающее на
     * качество выполнения задания.
     * </p>
     */
    @Column(name = "mark")
    private Integer mark;

    /**
     * Дата и время создания домашнего задания.
     * <p>
     * Устанавливается автоматически при создании задания и не может
     * быть обновлена после создания.
     * </p>
     */
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * Связанный ученик.
     * <p>
     * Связь "многие к одному" с сущностью {@link Student}.
     * Указывает, какой ученик выполнил данное домашнее задание.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "homeworks_student_id_fkey"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Student student;

    /**
     * Связанный урок.
     * <p>
     * Связь "многие к одному" с сущностью {@link Lesson}.
     * Указывает, к какому уроку относится данное домашнее задание.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;
}

