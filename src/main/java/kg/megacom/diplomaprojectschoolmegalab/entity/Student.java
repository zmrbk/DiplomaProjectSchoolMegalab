package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(name = "parent_status", nullable = false)
    private String parentStatus;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private StudentClass studentClass;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mark> marks;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;
}
