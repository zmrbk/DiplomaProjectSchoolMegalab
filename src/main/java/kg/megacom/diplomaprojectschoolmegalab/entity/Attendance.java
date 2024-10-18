package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность для представления посещаемости студентов.
 */
@Entity
@Table(name = "attendances")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    /**
     * Уникальный идентификатор записи о посещаемости.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Статус посещаемости.
     * true - присутствует, false - отсутствует.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private Boolean attended;

    /**
     * Студент, которому принадлежит запись о посещаемости.
     * Связь с сущностью Student.
     */
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    /**
     * Урок, на котором фиксируется посещаемость.
     * Связь с сущностью Lesson.
     */
    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;
}


