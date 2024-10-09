package kg.megacom.diplomaprojectschoolmegalab.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "creation_date")
    private LocalDateTime creationDate ;

    @PrePersist
    private void prePersist() {
        creationDate = LocalDateTime.now();
    }
}
