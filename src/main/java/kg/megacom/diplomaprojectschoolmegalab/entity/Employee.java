package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import kg.megacom.diplomaprojectschoolmegalab.enums.Position;
import lombok.*;

/**
 * Класс, представляющий сущность Employee (Сотрудник) в системе.
 * <p>
 * Каждому сотруднику соответствует определенная позиция и зарплата.
 * Связь с пользователем осуществляется через поле {@link User},
 * что позволяет отобразить связь "многие к одному",
 * где один пользователь может иметь несколько позиций сотрудников.
 * </p>
 *
 * @see User
 */
@Entity
@Table(name = "employees")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    /**
     * Уникальный идентификатор сотрудника.
     * <p>
     * Генерируется автоматически при создании нового сотрудника.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Позиция сотрудника.
     * <p>
     * Хранится в формате строки. Используется перечисление {@link Position}
     * для указания позиции.
     * </p>
     */
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    /**
     * Зарплата сотрудника.
     * <p>
     * Представляет собой целочисленное значение, определяющее
     * уровень зарплаты сотрудника.
     * </p>
     */
    @Column(name = "salary")
    private Integer salary;

    /**
     * Связанный пользователь.
     * <p>
     * Связь "многие к одному" с сущностью {@link User}.
     * Один пользователь может быть связан с несколькими
     * сотрудниками.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}

