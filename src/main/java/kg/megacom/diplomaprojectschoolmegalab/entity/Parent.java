package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import kg.megacom.diplomaprojectschoolmegalab.enams.JobTitle;
import lombok.*;

@Entity
@Table(name = "parents")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;
}
