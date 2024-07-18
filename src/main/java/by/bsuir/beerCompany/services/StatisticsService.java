package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.ProductStatisticsInfo;
import by.bsuir.beerCompany.dao.SalesStatisticsDto;
import by.bsuir.beerCompany.entity.Category;
import by.bsuir.beerCompany.entity.Product;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.repo.CategoryRepository;
import by.bsuir.beerCompany.repo.ProductRepository;
import by.bsuir.beerCompany.repo.PurchaseItemRepository;
import by.bsuir.beerCompany.repo.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final PurchaseItemRepository purchaseItemRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<SalesStatisticsDto> getSalesStatistics(){

        List<SalesStatisticsDto> salesStatisticsDtoList = new ArrayList<>();
        List<Category> allCategories = categoryRepository.findAll();

        for(Category category : allCategories){
            
            SalesStatisticsDto salesStatisticsDto = new SalesStatisticsDto();
            salesStatisticsDto.setCategoryName(category.getCategoryName());

            List<ProductStatisticsInfo> productStatisticsInfoList = new ArrayList<>();
            List<Product> productList = productRepository.findByCategory(category);
            
            for(Product product : productList){
                int soldQuantity = 0;
                int cartQuantity = 0;
                ProductStatisticsInfo item = new ProductStatisticsInfo();
                
                List<PurchaseItem> purchaseItems = purchaseItemRepository.findByProduct(product);

                for(PurchaseItem purchaseItem : purchaseItems){
                    if(purchaseItem.getPurchase() != null){ //Проверяем есть ли purchase_id у purchase_item
                        soldQuantity += purchaseItem.getQuantity(); //Если есть, то заказ был сделан
                    }
                    else{
                        cartQuantity += purchaseItem.getQuantity(); //Если нет, то purchase_item находится в корзине
                    }
                }

                item.setProductName(product.getDrink().getDrinkName());
                item.setCartQuantity(cartQuantity);
                item.setSoldQuantity(soldQuantity);
                productStatisticsInfoList.add(item);
            }

            salesStatisticsDto.setProductStatisticsInfoList(productStatisticsInfoList);
            salesStatisticsDtoList.add(salesStatisticsDto);
        }
        return salesStatisticsDtoList;
    }

}
