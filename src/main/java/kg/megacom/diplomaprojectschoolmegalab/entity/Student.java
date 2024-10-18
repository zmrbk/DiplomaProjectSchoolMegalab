package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Класс, представляющий студента в учебном заведении.
 * <p>
 * Этот класс содержит информацию о дате рождения студента,
 * классах, в которых он учится, оценках, связанных пользователях и родителях.
 * </p>
 */
@Entity
@Table(name = "students")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Student {

    /**
     * Уникальный идентификатор студента.
     * <p>
     * Генерируется автоматически при создании нового студента.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата рождения студента.
     * <p>
     * Обязательное поле, указывающее на день рождения студента.
     * </p>
     */
    @Column(nullable = false)
    private LocalDate birthday;

    /**
     * Класс, в котором учится студент.
     * <p>
     * Связь "многие к одному" с сущностью {@link StudentClass}.
     * Обязательное поле, указывающее на класс студента.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private StudentClass studentClass;

    /**
     * Оценки студента.
     * <p>
     * Связь "один ко многим" с сущностью {@link Mark}.
     * Все оценки студента будут удалены при удалении студента.
     * </p>
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mark> marks;

    /**
     * Пользователь, связанный с аккаунтом студента.
     * <p>
     * Связь "многие к одному" с сущностью {@link User}.
     * Обязательное поле, указывающее на пользователя.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Родитель студента.
     * <p>
     * Связь "многие к одному" с сущностью {@link Parent}.
     * Обязательное поле, указывающее на родителя студента.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    /**
     * Статус родителя.
     * <p>
     * Обязательное поле, указывающее на статус родителя студента, например: "опекун", "родитель" и т.д.
     * </p>
     */
    @Column(name = "parent_status", nullable = false)
    private String parentStatus;

    @Column(name = "class_captain", nullable = false)
    private Boolean isClassCaptain;

    @ManyToMany
    @JoinTable(
            name = "m2m_students_subjects",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects;
}
