package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
/**
 * Класс, представляющий отзыв.
 * <p>
 * Этот класс хранит информацию о отзыве, связанном с конкретным студентом
 * и его автором. Он также содержит дату создания отзыва.
 * </p>
 *
 * @see Student
 * @see User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    /**
     * Уникальный идентификатор отзыва.
     * <p>
     * Генерируется автоматически при создании нового отзыва.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Текст отзыва.
     * <p>
     * Обязательное поле, не может быть пустым.
     * </p>
     */
    @Column(nullable = false)
    private String review;

    /**
     * Связанный студент.
     * <p>
     * Связь "многие к одному" с сущностью {@link Student}.
     * Указывает на студента, к которому относится данный отзыв.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "reviews_student_id_fkey"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Student student;

    /**
     * Автор отзыва.
     * <p>
     * Связь "многие к одному" с сущностью {@link User}.
     * Указывает на пользователя, который оставил данный отзыв.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    /**
     * Дата создания отзыва.
     * <p>
     * Обязательное поле, автоматически устанавливается на текущую дату и время
     * при создании отзыва.
     * </p>
     */
    @Column(name = "creation_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creationDate;
}
