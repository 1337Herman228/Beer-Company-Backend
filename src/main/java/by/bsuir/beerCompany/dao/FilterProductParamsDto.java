package by.bsuir.beerCompany.dao;

import lombok.Data;

import java.util.List;

@Data
public class FilterProductParamsDto {
    List<String> categoryFilter;
    float minPrice;
    float maxPrice;
}
