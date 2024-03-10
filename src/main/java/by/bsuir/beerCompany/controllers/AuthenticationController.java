package by.bsuir.beerCompany.controllers;

import by.bsuir.beerCompany.dao.*;
import by.bsuir.beerCompany.services.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public AuthenticationResponse register(@RequestBody RegistrationRequest registrationRequest) {
//
//        return authenticationService.register(registrationRequest);
//    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(@RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewUserDao newUser = objectMapper.readValue(requestBody, NewUserDao.class);
        try{
            return authenticationService.register(newUser);
        }
        catch (Exception e){
            System.out.println("user " + newUser.getLogin() + " already exists");
            return new AuthenticationResponse("user already exists");
        }

    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(@RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationRequest authenticationRequest = objectMapper.readValue(requestBody, AuthenticationRequest.class);
        return authenticationService.authenticate(authenticationRequest);
    }
}
