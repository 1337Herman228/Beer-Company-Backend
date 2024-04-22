package by.bsuir.beerCompany.dao;

import lombok.Data;

@Data
public class MakePurchaseDto {
    private Long userId;
    private String payment;
    private String destination;
    private float finalPrice;
}
