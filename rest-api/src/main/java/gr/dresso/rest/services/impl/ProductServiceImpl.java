package gr.dresso.rest.services.impl;

import gr.dresso.rest.entities.Product;
import gr.dresso.rest.repositories.CategoryRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    @Override
    public List<Product> getAllProductsByName(String name) {
        return productRepository.findAllByName(name);
    }
    @Override
    public List<Product> getAllProductsByCategoryName(String categoryName) {
        return productRepository.findAllByCategoryName(categoryName);
    }
    @Override
    public List<Product> getAllProductsByNameAndCategoryName(String name, String category){
        return productRepository.findAllByNameAndCategoryName(name, category);
    }
}
