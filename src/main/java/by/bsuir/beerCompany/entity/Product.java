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

    @OneToOne
    @JoinColumn(name = "drinkId")
    private Drink drink;

    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private float price;
    private int quantityLeft;
    private String possibleVolume;

}
