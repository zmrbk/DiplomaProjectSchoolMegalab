package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import kg.megacom.diplomaprojectschoolmegalab.enums.Position;
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
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;
    @Column(name = "salary")
    private Integer salary;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;
}
