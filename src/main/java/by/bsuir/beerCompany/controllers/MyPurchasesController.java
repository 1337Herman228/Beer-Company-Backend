package by.bsuir.beerCompany.controllers;


import by.bsuir.beerCompany.dao.PurchaseDto;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.entity.Purchases;
import by.bsuir.beerCompany.repo.PurchaseRepository;
import by.bsuir.beerCompany.repo.UserRepository;
import by.bsuir.beerCompany.services.CartService;
import by.bsuir.beerCompany.services.JSONConvertingService;
import by.bsuir.beerCompany.services.PurchasesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class MyPurchasesController {

    private final JSONConvertingService jsonConvertingService;
    private final PurchasesService purchasesService;

    @GetMapping("/get-purchases/{userId}")
    public String getPurchases(@PathVariable Long userId) {
        List<PurchaseDto> purchasesDto = purchasesService.getPurchases(userId);
        return jsonConvertingService.convertObjectToJson(purchasesDto);
    }

    @PostMapping("/delete-purchase/{purchaseIdToDelete}")
    public ResponseEntity<String> deletePurchase(@PathVariable Long purchaseIdToDelete) {
        purchasesService.deletePurchase(purchaseIdToDelete);
        return ResponseEntity.ok("Ok");
    }
}
