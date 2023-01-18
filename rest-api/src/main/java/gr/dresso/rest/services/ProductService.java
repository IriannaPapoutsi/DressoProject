package gr.dresso.rest.services;

import gr.dresso.rest.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    // TODO: This method is not needed, you may just call the repository.findAll() directly
    List<Product> getAllProducts();
    List<Product> getAllProductsBasedOnParams(String name, String category);
}
