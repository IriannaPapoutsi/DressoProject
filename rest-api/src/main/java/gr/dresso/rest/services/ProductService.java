package gr.dresso.rest.services;

import gr.dresso.rest.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getAllProductsBasedOnParams(String name, String category);
}
