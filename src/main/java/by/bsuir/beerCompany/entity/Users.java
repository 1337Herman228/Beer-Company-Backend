package by.bsuir.beerCompany.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Table(name = "users")
@Accessors(chain = true)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(unique = true)
    private String login;

    private String password;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Roles roles;

    @OneToOne
    @JoinColumn(name = "personId")
    private Person person;

}
