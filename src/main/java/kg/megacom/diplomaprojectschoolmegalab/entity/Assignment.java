package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String assignment;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();
    @Column(name = "is_done", nullable = false)
    private Boolean isDone = false;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private Employee author;
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private User receiver;
}

