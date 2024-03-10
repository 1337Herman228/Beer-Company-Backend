package by.bsuir.beerCompany.dao;

import lombok.Data;

@Data
public class ProductDao {
    private Long drinkId;
    private Long categoryId;
    private String price;
    private String volume;
    private String quantity;
}
