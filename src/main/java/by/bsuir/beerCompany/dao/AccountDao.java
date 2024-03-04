package by.bsuir.beerCompany.dao;

import lombok.Data;

@Data
public class AccountDao {
    private Long userId;
    private String login;
    private String position;
    private String name;
    private String surname;
    private String phone;
    private String email;

    private String password = "1111";
}
