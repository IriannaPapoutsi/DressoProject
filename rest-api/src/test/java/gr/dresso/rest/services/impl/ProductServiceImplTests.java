package gr.dresso.rest.services.impl;

import gr.dresso.rest.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void getAllProductsByName_shouldCallProductRepositoryMethod() {
        // Given
        String name = "Elegant Dress";

        // When
        productService.getAllProductsByName(name);

        // Then
        verify(productRepository).findAllByNameContains(name);
    }

    @Test
    public void getAllProductsByCategoryName_shouldCallProductRepositoryMethod() {
        // Given
        String categoryName = "Coat";

        // When
        productService.getAllProductsByCategoryName(categoryName);

        // Then
        verify(productRepository).findAllByCategoryNameContains(categoryName);
    }

    @Test
    public void getAllProductsByNameAndCategoryName_shouldCallProductRepositoryMethod() {
        
        // Given
        String name = "Elegant Dress";
        String categoryName = "Dress";

        // When
        productService.getAllProductsByNameAndCategoryName(name, categoryName);

        // Then
        verify(productRepository).findAllByNameContainsAndCategoryNameContains(name, categoryName);
    }
    
}
