package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Сущность для представления задания.
 */
@Entity
@Table(name = "assignments")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {

    /**
     * Уникальный идентификатор задания.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Описание задания.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private String assignment;

    /**
     * Дата и время создания задания.
     * Устанавливается автоматически при создании.
     */
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * Статус выполнения задания.
     * По умолчанию - не выполнено (false).
     */
    @Column(name = "is_done", nullable = false)
    private Boolean isDone = false;

    /**
     * Автор задания.
     * Связь с сущностью Employee.
     * Не может быть пустым.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private User author;

    /**
     * Получатель задания.
     * Связь с сущностью User.
     * Не может быть пустым.
     */
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private User receiver;
}
