package by.bsuir.beerCompany.entity.Cart;

import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.entity.Users;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class Cart {

    @EmbeddedId
    private CartId id;

    @ManyToOne
    @MapsId("userId")
    private Users user;

    @ManyToOne
    @MapsId("purchaseItemId")
    private PurchaseItem purchaseItem;
}
