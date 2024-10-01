package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import kg.megacom.diplomaprojectschoolmegalab.enams.JobTitle;
import lombok.*;

@Entity
@Table(name = "employees")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private JobTitle position;
    private Integer salary;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;
}
