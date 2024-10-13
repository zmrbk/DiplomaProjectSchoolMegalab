package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс, представляющий сущность Parent (Родитель) в системе.
 * <p>
 * Этот класс содержит информацию о родителе, связанного с
 * учетной записью пользователя. Каждый родитель может иметь
 * один связанный пользовательский аккаунт, что позволяет
 * хранить информацию о родителе в системе.
 * </p>
 *
 * @see User
 */
@Entity
@Table(name = "parents")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parent {

    /**
     * Уникальный идентификатор родителя.
     * <p>
     * Генерируется автоматически при создании нового родителя.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Связанный пользовательский аккаунт.
     * <p>
     * Связь "один к одному" с сущностью {@link User}.
     * Указывает на пользователя, который является родителем.
     * </p>
     */
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;
}

