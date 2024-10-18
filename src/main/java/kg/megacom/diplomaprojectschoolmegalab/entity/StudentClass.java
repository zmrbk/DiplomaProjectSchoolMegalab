package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс, представляющий учебный класс в учебном заведении.
 * <p>
 * Этот класс содержит информацию о названии класса,
 * связанном учителе и дате создания.
 * </p>
 */
@Entity
@Table(name = "student_classes")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentClass {

    /**
     * Уникальный идентификатор учебного класса.
     * <p>
     * Генерируется автоматически при создании нового класса.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название учебного класса.
     * <p>
     * Обязательное поле, указывающее на название класса.
     * </p>
     */
    @Column(name = "class_title")
    private String classTitle;

    /**
     * Учитель, ответственный за класс.
     * <p>
     * Связь "один к одному" с сущностью {@link Employee}.
     * Указывает на учителя, который ведет данный класс.
     * </p>
     */
    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Employee employee;

    /**
     * Дата создания класса.
     * <p>
     * Указывает на момент создания данного класса.
     * Значение по умолчанию устанавливается на текущее время.
     * </p>
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @OneToMany(mappedBy = "studentClass")
    private List<Student> students;
}