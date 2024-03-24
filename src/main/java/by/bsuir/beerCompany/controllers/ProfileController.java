package by.bsuir.beerCompany.controllers;

import by.bsuir.beerCompany.dao.ChangePasswordDto;
import by.bsuir.beerCompany.dao.ChangePersonDto;
import by.bsuir.beerCompany.dao.ChangeUserDto;
import by.bsuir.beerCompany.entity.Person;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.entity.Users;
import by.bsuir.beerCompany.repo.PersonRepository;
import by.bsuir.beerCompany.repo.UserRepository;
import by.bsuir.beerCompany.services.JSONConvertingService;
import by.bsuir.beerCompany.services.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final JSONConvertingService jsonConvertingService;
    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    @GetMapping("/get-user-info/{userId}")
    public String getUserInfo(@PathVariable Long userId) throws JsonProcessingException {
        Users user = userRepository.findById(userId).get();
        return jsonConvertingService.convertObjectToJson(user);
    }

    @GetMapping("/get-person-info/{userId}")
    public String getPersonInfo(@PathVariable Long userId) throws JsonProcessingException {
        Users user = userRepository.findById(userId).get();
        Person person = user.getPerson();
        return jsonConvertingService.convertObjectToJson(person);
    }

    @PostMapping("/change-password/{userId}")
    public String changePassword(@PathVariable Long userId, @RequestBody String requestBody) throws JsonProcessingException {
        boolean res = profileService.changePassword(userId, requestBody);
        if(res){
            return jsonConvertingService.convertObjectToJson(userRepository.findById(userId).get());
        }else{
            return jsonConvertingService.convertObjectToJson("error");
        }
    }

    @PostMapping("/change-user-data/{userId}")
    public String changeUserData(@PathVariable Long userId , @RequestBody String requestBody) throws JsonProcessingException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ChangeUserDto userDto = objectMapper.readValue(requestBody, ChangeUserDto.class);

            Users user = userRepository.findById(userId).get();
            user.setLogin(userDto.getLogin());
            userRepository.save(user);
            return jsonConvertingService.convertObjectToJson(user);
        }
        catch (Exception e){
            return jsonConvertingService.convertObjectToJson("error");
        }

    }

    @PostMapping("/change-person-data/{userId}")
    public String changePersonData(@PathVariable Long userId , @RequestBody String requestBody) throws JsonProcessingException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ChangePersonDto changePersonDto = objectMapper.readValue(requestBody, ChangePersonDto.class);
            Users user = userRepository.findById(userId).get();
            Person person = personRepository.findById(user.getPerson().getPersonId()).get();
            person.setName(changePersonDto.getName());
            person.setSurname(changePersonDto.getSurname());
            person.setPhone(changePersonDto.getPhone());
            person.setEmail(changePersonDto.getEmail());
            personRepository.save(person);
            return jsonConvertingService.convertObjectToJson(person);
        }
        catch (Exception e){
            return jsonConvertingService.convertObjectToJson("error");
        }

    }
}
