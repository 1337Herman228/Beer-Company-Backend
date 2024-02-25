package by.bsuir.beerCompany.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "purchase_item")
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseItemId;

    @ManyToOne
    @JoinColumn(name = "purchaseId")
    private Purchases purchase;

//    @OneToOne
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private int quantity;
    private float volume;
    private float totalPrice;
}
