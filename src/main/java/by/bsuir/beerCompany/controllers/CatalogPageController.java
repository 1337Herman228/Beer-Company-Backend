package by.bsuir.beerCompany.controllers;

import by.bsuir.beerCompany.entity.Product;
import by.bsuir.beerCompany.repo.CategoryRepository;
import by.bsuir.beerCompany.repo.ProductRepository;
import by.bsuir.beerCompany.services.CatalogService;
import by.bsuir.beerCompany.services.JSONConvertingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogPageController {

    private final JSONConvertingService jsonConvertingService;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CatalogService catalogService;

    @GetMapping("/categories")
    public String categories() {
        return jsonConvertingService.convertObjectToJson(categoryRepository.findAll());
    }

    @GetMapping("/products")
    public String products() {
        return jsonConvertingService.convertObjectToJson(productRepository.findAll());
    }

    @GetMapping("/get-product/{productId}")
    public String getSingleProduct(@PathVariable Long productId) {
        Product singleProduct = catalogService.getSingleProduct(productId);
        return jsonConvertingService.convertObjectToJson(singleProduct);
    }

    @GetMapping("/min-max-prices")
    public String getMinMaxPrices() {
        List<Float> minMaxPrices = catalogService.getMinMaxPrices();
        return jsonConvertingService.convertObjectToJson(minMaxPrices);
    }


    @PostMapping("/filter-products")
    public String getFilteredProducts(@RequestBody String requestBody) throws JsonProcessingException {
        List<Product> filteredProducts = catalogService.filterProducts(requestBody);
        return jsonConvertingService.convertObjectToJson(filteredProducts);
    }
}
