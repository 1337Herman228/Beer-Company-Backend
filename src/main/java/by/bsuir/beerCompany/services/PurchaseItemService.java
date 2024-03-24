package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.repo.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PurchaseItemService {

    private ProductRepository productRepository;
    public PurchaseItem createPurchaseItem(
        Long productId,
        int quantity,
        float volume,
        float totalPrice ){

            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setProduct(productRepository.findById(productId).get());
            purchaseItem.setQuantity(quantity);
            purchaseItem.setVolume(volume);
            purchaseItem.setTotalPrice(totalPrice);
            return purchaseItem;
    }
}
