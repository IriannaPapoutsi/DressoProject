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
