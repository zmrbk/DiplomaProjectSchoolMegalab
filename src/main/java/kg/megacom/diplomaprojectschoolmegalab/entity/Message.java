package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Класс, представляющий сущность Message (Сообщение) в системе.
 * <p>
 * Этот класс содержит информацию о сообщении, отправленном
 * от одного пользователя (автора) другому пользователю (получателю).
 * Каждое сообщение включает текст сообщения, дату создания,
 * статус прочтения и ссылки на автора и получателя.
 * </p>
 *
 * @see Employee
 * @see User
 */
@Entity
@Table(name = "messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    /**
     * Уникальный идентификатор сообщения.
     * <p>
     * Генерируется автоматически при создании нового сообщения.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Текст сообщения.
     * <p>
     * Хранит содержимое сообщения. Не может быть null.
     * </p>
     */
    @Column(nullable = false)
    private String message;

    /**
     * Дата и время создания сообщения.
     * <p>
     * Заполняется автоматически при создании сообщения.
     * Не может быть null.
     * </p>
     */
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * Статус прочтения сообщения.
     * <p>
     * Указывает, было ли сообщение прочитано. Значение по умолчанию - false.
     * </p>
     */
    @Column(name = "is_read", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isRead;

    /**
     * Связанный автор сообщения.
     * <p>
     * Связь "многие к одному" с сущностью {@link Employee}.
     * Указывает на автора, отправившего сообщение.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Employee author;

    /**
     * Связанный получатель сообщения.
     * <p>
     * Связь "многие к одному" с сущностью {@link User}.
     * Указывает на пользователя, которому предназначено сообщение.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;
}
