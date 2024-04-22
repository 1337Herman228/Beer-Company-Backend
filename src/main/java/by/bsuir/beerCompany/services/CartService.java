package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.AddToCartDto;
import by.bsuir.beerCompany.dao.FilterProductParamsDto;
import by.bsuir.beerCompany.dao.MakePurchaseDto;
import by.bsuir.beerCompany.entity.Cart.Cart;
import by.bsuir.beerCompany.entity.Cart.CartId;
import by.bsuir.beerCompany.entity.Product;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.entity.Purchases;
import by.bsuir.beerCompany.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final PurchaseItemService purchaseItemService;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public void addToCart(String requestBody) throws JsonProcessingException {
        try{
           ObjectMapper objectMapper = new ObjectMapper();
           AddToCartDto addToCartDto = objectMapper.readValue(requestBody, AddToCartDto.class);

           List<PurchaseItem> purchaseItems = getPurchaseItemsFromCart(addToCartDto.getUserId());
           for( PurchaseItem item: purchaseItems){
               if(item.getProduct().getProductId() == addToCartDto.getProductId()
                       && item.getVolume() == addToCartDto.getVolume()){
                   item.setQuantity(item.getQuantity() + addToCartDto.getQuantity());
                   item.setTotalPrice(item.getTotalPrice() + addToCartDto.getTotalPrice());
                   purchaseItemRepository.save(item);
                   return;
               }
           }

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

    public void makeNewPurchase(String requestBody) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        MakePurchaseDto purchaseDto = objectMapper.readValue(requestBody, MakePurchaseDto.class);

        List<PurchaseItem> purchaseItems = getPurchaseItemsFromCart(purchaseDto.getUserId());

        for(PurchaseItem item: purchaseItems){
            Product product = item.getProduct();
            String[] volumeList = product.getPossibleVolume().split(";");
            String[] quantityList = product.getQuantityLeft().split(";");

            for(int i = 0; i < volumeList.length; i++){

                if(Float.parseFloat(volumeList[i]) == item.getVolume()){

                    if(Integer.parseInt(quantityList[i]) >= item.getQuantity() ){

                        quantityList[i]  = quantityList[i].replaceFirst(quantityList[i], String.valueOf(Integer.parseInt(quantityList[i]) - item.getQuantity()));

                        String changedQuantity = "";
                        for(int j = 0; j < quantityList.length - 1; j++){
                            changedQuantity += quantityList[j] + ";";
                        }
                        changedQuantity += quantityList[quantityList.length - 1];
                        product.setQuantityLeft(changedQuantity);

                    }
                    else{
                        throw new IllegalArgumentException("Not enough quantity");
                    }
                }
            }
            productRepository.save(product);
        }

        Purchases purchase = new Purchases();
        purchase.setDestination(purchaseDto.getDestination());
        purchase.setPayment(purchaseDto.getPayment());
        purchase.setFinalPrice(purchaseDto.getFinalPrice());

        for(PurchaseItem item: purchaseItems){
            item.setPurchase(purchase);
        }
        purchase.setUser(userRepository.findById(purchaseDto.getUserId()).get());
        purchase.setDate(new Date());

//        purchase.setItems(purchaseItems); /////

        purchaseRepository.save(purchase);
        deleteItemsFromCartAfterPurchase(purchaseDto.getUserId());
    }

    public void deleteItemsFromCartAfterPurchase(Long userId) {
        List<Cart> carts = cartRepository.findByUser(userRepository.findById(userId).get());
        for(Cart cart: carts){
            if(cart.getPurchaseItem().getPurchase() != null){
                cartRepository.delete(cart);
            }
        }
    }


}
