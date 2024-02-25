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

//    @OneToOne
    @ManyToOne
    @JoinColumn(name = "drinkId")
    private Drink drink;

//    @OneToOne
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private float price;
    private int quantityLeft;
    private String possibleVolume;

}
