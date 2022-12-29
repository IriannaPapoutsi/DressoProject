package gr.dresso.rest.controllers;

import gr.dresso.rest.entities.Product;
import gr.dresso.rest.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String name, @RequestParam(required = false) String category){
        if (name != null && category != null) {
            return productService.getAllProductsByNameAndCategoryName(name, category);
        }
        else if (name != null) {
            return productService.getAllProductsByName(name);
        }
        else if (category != null) {
            return productService.getAllProductsByCategoryName(category);
        }
        else {
            return productService.getAllProducts();
        }
    }
}
