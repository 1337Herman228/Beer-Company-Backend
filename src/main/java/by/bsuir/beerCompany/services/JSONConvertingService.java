package by.bsuir.beerCompany.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JSONConvertingService {
    public <T> String convertObjectToJson(T yourObject){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(yourObject);
        }catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
