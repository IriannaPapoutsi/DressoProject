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
    // TODO: Newline here
    private final ProductService productService;
    // TODO: Newline here
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    // TODO: This line is too long, move the second parameter on next line
    public List<Product> getAllProducts(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        // TODO: I believe that all these "If" branches would look better if you moved them into another method in the Service class, instead of having it on the controller
        // TODO: Here, you may call one method like you do on the other controllers
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
    // TODO: Newline here
}
