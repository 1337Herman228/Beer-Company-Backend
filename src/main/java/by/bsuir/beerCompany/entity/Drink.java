package by.bsuir.beerCompany.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "drink")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long drinkId;

    private String drinkName;
    @Column(length = 1000)
    private String shortDescription;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String compound;
    @Column(length = 25000)
    private String image;
}
