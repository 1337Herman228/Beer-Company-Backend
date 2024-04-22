package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.FilterProductParamsDto;
import by.bsuir.beerCompany.dao.ProductDao;
import by.bsuir.beerCompany.entity.Product;
import by.bsuir.beerCompany.repo.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CatalogService {

    private ProductRepository productRepository;
    public List<Product> filterProducts(String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterProductParamsDto filterProductParamsDto = objectMapper.readValue(requestBody, FilterProductParamsDto.class);

        List<Product> filteredProducts = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll();

        for(Product product: allProducts){

            boolean isCategoryOk = !filterProductParamsDto.getCategoryFilter().isEmpty();
            boolean isPriceOk = false;

            if(!filterProductParamsDto.getCategoryFilter().isEmpty()){
                if(!filterProductParamsDto.getCategoryFilter().contains(product.getCategory().getCategoryName())){
                    isCategoryOk = false;
                }
            }
            String[] price = product.getPrice().split(";");
            for (int i = 0; i < price.length; i++) {
                float priceFloat = Float.parseFloat(price[i]);
                if (priceFloat >= filterProductParamsDto.getMinPrice() && priceFloat <= filterProductParamsDto.getMaxPrice()) {
                    isPriceOk = true;
                    i = price.length + 1;
                }
            }
            if(isCategoryOk && isPriceOk){
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public List<Float> getMinMaxPrices(){
        List<Float> allPrices = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for(Product product: products){
            String[] price = product.getPrice().split(";");
            Arrays.stream(price).map(Float::parseFloat).forEach(allPrices::add);
        }
        allPrices.sort(Float::compareTo);
//        System.out.println(allPrices);
        List<Float> minMaxPrices = new ArrayList<>();
        minMaxPrices.add(allPrices.get(0));
        minMaxPrices.add(allPrices.get(allPrices.size()-1));
        return minMaxPrices;
    }

    public Product getSingleProduct(Long productId){
        return productRepository.findById(productId).orElseThrow();
    }
}
