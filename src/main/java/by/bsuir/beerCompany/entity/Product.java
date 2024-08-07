package by.bsuir.beerCompany.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "drinkId")
    private Drink drink;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private String price;
    private String quantityLeft;
    private String possibleVolume;

}
