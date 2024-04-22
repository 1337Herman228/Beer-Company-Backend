package by.bsuir.beerCompany.dao;

import by.bsuir.beerCompany.entity.PurchaseItem;
import lombok.Data;

import java.util.List;

@Data
public class SalesStatisticsDto {
    private String categoryName;
    private List<ProductStatisticsInfo> productStatisticsInfoList;
}
