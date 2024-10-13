package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedules")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;
    @Column(name = "quarter", nullable = false)
    private Short quarter;
    @Column(name = "due_time", nullable = false)
    private String dueTime;
    @Column(name = "year", nullable = false)
    private String year;
    @Column(name = "is_approve", nullable = false)
    private Boolean isApprove;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Employee teacher;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    private StudentClass studentClass;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;
}

