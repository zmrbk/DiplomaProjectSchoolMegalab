package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность для представления уставов (чартеров).
 */
@Entity
@Data
@RequiredArgsConstructor
@Table(name = "charters")
public class Charter {

    /**
     * Уникальный идентификатор устава.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Заголовок устава.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Описание устава.
     * Не может быть пустым.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Сотрудник, который связан с уставом.
     * Связь с сущностью Employee.
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    /**
     * Дата создания устава.
     * Не может быть пустой.
     */
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();
}
