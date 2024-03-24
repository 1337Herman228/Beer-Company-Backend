package by.bsuir.beerCompany.controllers;

import by.bsuir.beerCompany.dao.*;
import by.bsuir.beerCompany.repo.UserRepository;
import by.bsuir.beerCompany.services.AuthenticationService;
import by.bsuir.beerCompany.services.JSONConvertingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JSONConvertingService jsonConvertingService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(@RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewUserDao newUser = objectMapper.readValue(requestBody, NewUserDao.class);
        try{
            return authenticationService.register(newUser);
        }
        catch (Exception e){
            return new AuthenticationResponse("user already exists");
        }

    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public String authenticate(@RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationRequest authenticationRequest = objectMapper.readValue(requestBody, AuthenticationRequest.class);
        try{
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
            AuthDto authDto = new AuthDto();
            authDto.setAuthenticationResponse(authenticationResponse);
            authDto.setRole(userRepository.findByLogin(authenticationRequest.login()).orElseThrow().getRoles().getPosition());
            authDto.setUserId(userRepository.findByLogin(authenticationRequest.login()).orElseThrow().getUserId());
            return jsonConvertingService.convertObjectToJson(authDto);

        }catch (Exception e){
            String error = "error";
            return jsonConvertingService.convertObjectToJson(error);
        }
    }
}
