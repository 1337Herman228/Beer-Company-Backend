package by.bsuir.beerCompany.dao;

import lombok.Data;

@Data
public class AddToCartDto {
    private Long productId;
    private Long userId;
    private int quantity;
    private float volume;
    private float totalPrice;
}
