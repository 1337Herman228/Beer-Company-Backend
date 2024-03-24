package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.AddToCartDto;
import by.bsuir.beerCompany.dao.ChangePasswordDto;
import by.bsuir.beerCompany.entity.Users;
import by.bsuir.beerCompany.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean changePassword(Long userId, String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChangePasswordDto changePasswordDto = objectMapper.readValue(requestBody, ChangePasswordDto.class);
        try{
            Users user = userRepository.findById(userId).get();
            if(passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                userRepository.save(user);
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
