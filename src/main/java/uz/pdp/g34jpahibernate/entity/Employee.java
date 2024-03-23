package uz.pdp.g34jpahibernate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String position;

    private Double salary;

    @OneToOne(cascade = {CascadeType.PERSIST}, mappedBy = "employee")
    private Address address;

    @ManyToOne
    private Department department;
}
