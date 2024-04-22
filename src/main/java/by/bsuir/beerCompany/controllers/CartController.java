package by.bsuir.beerCompany.controllers;

import by.bsuir.beerCompany.entity.Product;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.services.CartService;
import by.bsuir.beerCompany.services.JSONConvertingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JSONConvertingService jsonConvertingService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody String requestBody) throws JsonProcessingException {
        cartService.addToCart(requestBody);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/del-item-from-cart/{itemId}/{userId}")
    public String delFromCart(@PathVariable Long itemId, @PathVariable Long userId) throws JsonProcessingException {
        List<PurchaseItem> purchaseItems = cartService.deleteFromCart(itemId,userId);
        return jsonConvertingService.convertObjectToJson(purchaseItems);
    }

    @GetMapping("/get-purchase-items/{userId}")
    public String getPurchaseItems(@PathVariable Long userId) throws JsonProcessingException {
        List<PurchaseItem> purchaseItems = cartService.getPurchaseItemsFromCart(userId);
        return jsonConvertingService.convertObjectToJson(purchaseItems);
    }

    @PostMapping("/make-purchase")
    public String makePurchase(@RequestBody String requestBody) throws JsonProcessingException {
        try{
            cartService.makeNewPurchase(requestBody);
            return jsonConvertingService.convertObjectToJson("ok");
        }catch (Exception e){
            return jsonConvertingService.convertObjectToJson("error");
        }
    }
}
