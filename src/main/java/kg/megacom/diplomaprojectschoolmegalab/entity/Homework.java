package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "homeworks")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_done", nullable = false)
    private Boolean isDone = false;
    @Column(name = "teacher_review")
    private String teacherReview;
    @Column(name = "mark")
    private Integer mark;
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;
}
