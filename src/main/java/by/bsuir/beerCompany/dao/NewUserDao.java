package by.bsuir.beerCompany.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NewUserDao {
    private String login;
    private String password;
    private final String position = "ROLE_USER";
    private String name;
    private String surname;
    private String phone;
    private String email;
}
