package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attendances")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Boolean attended;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;
}

