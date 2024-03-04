package by.bsuir.beerCompany.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Table(name = "person")
@Accessors(chain = true)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long personId;

    private String name;
    private String surname;
    private String phone;
    private String email;

}
