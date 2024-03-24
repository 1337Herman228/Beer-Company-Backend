package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.AddToCartDto;
import by.bsuir.beerCompany.dao.FilterProductParamsDto;
import by.bsuir.beerCompany.entity.Cart.Cart;
import by.bsuir.beerCompany.entity.Cart.CartId;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.repo.CartRepository;
import by.bsuir.beerCompany.repo.ProductRepository;
import by.bsuir.beerCompany.repo.PurchaseItemRepository;
import by.bsuir.beerCompany.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final PurchaseItemService purchaseItemService;
    private final PurchaseItemRepository purchaseItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public void addToCart(String requestBody) throws JsonProcessingException {
        try{
           ObjectMapper objectMapper = new ObjectMapper();
           AddToCartDto addToCartDto = objectMapper.readValue(requestBody, AddToCartDto.class);

           PurchaseItem purchaseItem = purchaseItemService.createPurchaseItem(
               addToCartDto.getProductId(),
               addToCartDto.getQuantity(),
               addToCartDto.getVolume(),
               addToCartDto.getTotalPrice()
           );
           purchaseItemRepository.save(purchaseItem);
           CartId cartId = new CartId();
           cartId.setPurchaseItemId(purchaseItem.getPurchaseItemId());
           cartId.setUserId(addToCartDto.getUserId());
           Cart cart = new Cart();
           cart.setId(cartId);
           cart.setPurchaseItem(purchaseItem);
           cart.setUser(userRepository.findById(addToCartDto.getUserId()).get());
           cartRepository.save(cart);

       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<PurchaseItem> getPurchaseItemsFromCart(Long userId) {
        List<Cart> carts = cartRepository.findByUser(userRepository.findById(userId).get());
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for(Cart cart: carts){
            purchaseItems.add(cart.getPurchaseItem());
        }
        return purchaseItems;
    }

    public List<PurchaseItem> deleteFromCart(Long pItemId, Long userId) {
        cartRepository.delete(cartRepository.findByPurchaseItem(purchaseItemRepository.findById(pItemId).get()).get());
        purchaseItemRepository.deleteById(pItemId);
        return getPurchaseItemsFromCart(userId);
    }
}
