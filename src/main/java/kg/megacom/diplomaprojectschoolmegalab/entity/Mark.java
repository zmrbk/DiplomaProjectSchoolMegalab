package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marks")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mark", nullable = false)
    private Integer mark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student; // Assuming Student entity exists
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id", nullable = false)
    private Lesson lesson; // The Lesson entity to which the mark belongs
}
