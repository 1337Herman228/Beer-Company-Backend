package by.bsuir.beerCompany.dao;

import by.bsuir.beerCompany.entity.PurchaseItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PurchaseDto {
    private Long purchaseId;
    private Long userId;
    private String destination;
    private String payment;
    private String date;
    private float finalPrice;
    private List<PurchaseItem> items = new ArrayList<>();
}
