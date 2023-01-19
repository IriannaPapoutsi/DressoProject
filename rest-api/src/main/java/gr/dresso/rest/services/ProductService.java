package gr.dresso.rest.services;

import gr.dresso.rest.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getAllProductsBasedOnParams(String name, String category);

}
