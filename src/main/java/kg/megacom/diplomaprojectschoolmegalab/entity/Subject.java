package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Класс, представляющий предмет в учебном заведении.
 * <p>
 * Этот класс содержит информацию о названии предмета и его описании.
 * </p>
 */
@Entity
@Table(name = "subjects")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Subject {

    /**
     * Уникальный идентификатор предмета.
     * <p>
     * Генерируется автоматически при создании нового предмета.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название предмета.
     * <p>
     * Обязательное поле, указывающее на название данного предмета.
     * </p>
     */
    @Column(name = "subject_title")
    private String title;

    /**
     * Описание предмета.
     * <p>
     * Необязательное поле, содержащее дополнительную информацию о предмете.
     * </p>
     */
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students;

    // Define the OneToMany relationship between Subject and Topic
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Topic> topics;
}