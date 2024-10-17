package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Класс, представляющий сущность Lesson (Урок) в системе.
 * <p>
 * Этот класс содержит информацию о уроке, включая тему,
 * домашнее задание и связь с расписанием. Урок также может
 * иметь множество оценок, связанных с ним.
 * </p>
 *
 * @see Schedule
 * @see Mark
 */
@Entity
@Table(name = "lessons")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    /**
     * Уникальный идентификатор урока.
     * <p>
     * Генерируется автоматически при создании нового урока.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тема урока.
     * <p>
     * Хранит текстовое описание темы, которая будет изучаться
     * на данном уроке.
     * </p>
     */
    @Column(name = "topic")
    private String topic;

    /**
     * Домашнее задание, назначенное на урок.
     * <p>
     * Хранит текстовое описание домашнего задания, которое
     * ученики должны выполнить после урока.
     * </p>
     */
    @Column(name = "homework")
    private String homework;

    /**
     * Связанное расписание.
     * <p>
     * Связь "многие к одному" с сущностью {@link Schedule}.
     * Указывает на расписание, к которому принадлежит данный
     * урок.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id", nullable = false)
    private Schedule schedule;

    /**
     * Дата и время создания урока.
     * <p>
     * Устанавливается автоматически при создании урока
     * и не может быть обновлена после создания.
     * </p>
     */
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * Набор оценок, связанных с уроком.
     * <p>
     * Связь "один ко многим" с сущностью {@link Mark}.
     * Хранит оценки, выставленные ученикам за выполнение
     * заданий на данном уроке.
     * </p>
     */
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Mark> marks;


}

