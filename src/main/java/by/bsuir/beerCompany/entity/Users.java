package by.bsuir.beerCompany.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(unique = true)
    private String login;

    private String password;

//    @OneToOne
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Roles roles;

    @OneToOne
    @JoinColumn(name = "personId")
    private Person person;
}
