package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_classes")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "class_title")
    private String classTitle;
    @Column(name = "creation_date")
    private LocalDateTime creationDate  = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Employee employee;
}
