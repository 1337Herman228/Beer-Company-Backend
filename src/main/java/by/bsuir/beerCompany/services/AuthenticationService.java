package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.AuthenticationRequest;
import by.bsuir.beerCompany.dao.AuthenticationResponse;
import by.bsuir.beerCompany.dao.NewUserDao;
import by.bsuir.beerCompany.dao.RegistrationRequest;

public interface AuthenticationService {

    AuthenticationResponse register(NewUserDao newUserDao);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
