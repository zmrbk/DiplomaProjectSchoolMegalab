package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "lessons")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "topic")
    private String topic;
    @Column(name = "homework")
    private String homework;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id", nullable = false)
    private Schedule schedule;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Mark> marks;
}
