package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    // Many topics can be related to one subject
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
