package by.bsuir.beerCompany.dao;

import lombok.Data;

@Data
public class ProductStatisticsInfo {
    private String productName;
    private int soldQuantity;
    private int cartQuantity;
}
