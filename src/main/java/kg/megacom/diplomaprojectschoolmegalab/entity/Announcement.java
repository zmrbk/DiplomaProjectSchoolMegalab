package kg.megacom.diplomaprojectschoolmegalab.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Сущность для представления объявления.
 */
@Entity
@Table(name = "announcements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    /**
     * Уникальный идентификатор объявления.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Заголовок объявления.
     */
    @Column(name = "title")
    private String title;

    /**
     * Описание объявления.
     */
    @Column(name = "description")
    private String description;

    /**
     * Автор объявления.
     */
    @Column(name = "author")
    private String author;

    /**
     * Дата и время создания объявления.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Метод, вызываемый перед сохранением сущности,
     * устанавливающий дату и время создания.
     */
    @PrePersist
    private void prePersist() {
        creationDate = LocalDateTime.now();
    }
}
