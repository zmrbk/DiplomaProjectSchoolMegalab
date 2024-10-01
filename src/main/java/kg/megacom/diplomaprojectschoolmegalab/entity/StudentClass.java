package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_class")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gradeTitle;
    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Employee employee;
    @Column(name = "creation_date")
    private LocalDateTime creationDate  = LocalDateTime.now();
}
