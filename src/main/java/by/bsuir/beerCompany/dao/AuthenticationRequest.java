package by.bsuir.beerCompany.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public record AuthenticationRequest (
        String login,
        String password
){

}
