package by.bsuir.beerCompany.controllers;

import by.bsuir.beerCompany.entity.Users;
import by.bsuir.beerCompany.services.JSONConvertingService;
import by.bsuir.beerCompany.services.StatisticsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final JSONConvertingService jsonConvertingService;
    private final StatisticsService statisticsService;

    @GetMapping("/get-sales-statistics")
    public String getSalesStatistics() {
        return jsonConvertingService.convertObjectToJson(statisticsService.getSalesStatistics());
    }

}
