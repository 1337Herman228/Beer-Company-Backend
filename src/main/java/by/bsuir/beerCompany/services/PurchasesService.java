package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.PurchaseDto;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.entity.Purchases;
import by.bsuir.beerCompany.repo.PurchaseItemRepository;
import by.bsuir.beerCompany.repo.PurchaseRepository;
import by.bsuir.beerCompany.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchasesService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final UserRepository userRepository;

    public List<PurchaseDto> getAllPurchases() {
        List<Purchases> purchases = purchaseRepository.findAll();
        return mapPurchaseDto(purchases);
    }

    public List<PurchaseDto> getPurchases(Long userId) {
        List<Purchases> purchases = purchaseRepository.findByUser(userRepository.findById(userId).get());
        return mapPurchaseDto(purchases);
    }

    public List<PurchaseDto> mapPurchaseDto(List<Purchases> purchases) {
        List<PurchaseDto> dtoList = new ArrayList<>();
        for (Purchases purchase : purchases) {
            PurchaseDto dto = new PurchaseDto();
            dto.setPurchaseId(purchase.getPurchaseId());
            dto.setUserId(purchase.getUser().getUserId());
            dto.setDestination(purchase.getDestination());
            dto.setPayment(purchase.getPayment());
            String date = purchase.getDate().toString();
            dto.setDate(date);
            dto.setFinalPrice(purchase.getFinalPrice());
            dto.setItems(purchaseItemRepository.findByPurchase(purchase));
            dtoList.add(dto);
        }
        return dtoList;
    }

    public void deletePurchase(Long purchaseIdToDelete) {
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findByPurchase(purchaseRepository.findById(purchaseIdToDelete).get());
        purchaseItemRepository.deleteAll(purchaseItemList);
        purchaseRepository.deleteById(purchaseIdToDelete);
    }
}
