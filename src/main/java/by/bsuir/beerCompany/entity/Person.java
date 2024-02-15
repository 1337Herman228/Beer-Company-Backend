package by.bsuir.beerCompany.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long personId;

    private String name;
    private String surname;
    private String phone;
    private String email;

}
