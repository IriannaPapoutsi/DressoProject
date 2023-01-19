package gr.dresso.rest.services.impl;

import gr.dresso.rest.entities.Product;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private List<Product> getAllProductsByName(String name) {
        return productRepository.findAllByNameContains(name);
    }

    private List<Product> getAllProductsByCategoryName(String categoryName) {
        return productRepository.findAllByCategoryNameContains(categoryName);
    }

    private List<Product> getAllProductsByNameAndCategoryName(String name, String category){
        return productRepository.findAllByNameContainsAndCategoryNameContains(name, category);
    }

    @Override
    public List<Product> getAllProductsBasedOnParams(String name, String category) {
        if (name != null && category != null) {
            return getAllProductsByNameAndCategoryName(name, category);
        }
        else if (name != null) {
            return getAllProductsByName(name);
        }
        else if (category != null) {
            return getAllProductsByCategoryName(category);
        }
        else {
            return productRepository.findAll();
        }
    }

}
