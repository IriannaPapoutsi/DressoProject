package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.FavoriteProductDTO;
import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.repositories.FavoriteProductRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteProductServiceImplTests {
    @Mock
    private FavoriteProductRepository favoriteProductRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FavoriteProductServiceImpl favoriteProductService;

    @Test
    public void createFavoriteProductEntityFromFTO_shouldReturnCreatedFavoriteProductObject() {
        // Given
        FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO("1", "3");
        User user = User.builder()
                .id("1")
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("IT456")
                .country("Italy")
                .city("Turin, San Salvario")
                .address("Via Po 23")
                .email("marpap@gmail.com")
                .credits(15.0)
                .build();
        Product product = Product.builder()
                .id("3")
                .name("Satin Dress")
                .price(134.5)
                .description("Modern elegant dress for classy night outs!")
                .sku("gyht78")
                .stock(123)
                .build();
        FavoriteProduct expectedFavoriteProduct = new FavoriteProduct();
        expectedFavoriteProduct.setUser(user);
        expectedFavoriteProduct.setProduct(product);
        when(userRepository.findUserById("1")).thenReturn(user);
        when(productRepository.findProductById("3")).thenReturn(product);

        // When
        FavoriteProduct actualFavoriteProduct = favoriteProductService.createFavoriteProductEntityFromDTO(favoriteProductDTO);

        // Then
        assertEquals("createFavoriteProductEntityFromDTO() should return the created favorite product",expectedFavoriteProduct, actualFavoriteProduct);
    }

    @Test
    public void getProductObjectList_shouldReturnOnlyProductsObjecsFromFavoriteProductsList() {
        // Given
        String userId = "1";
        User user = User.builder()
                .id("1")
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("IT456")
                .country("Italy")
                .city("Turin, San Salvario")
                .address("Via Po 23")
                .email("marpap@gmail.com")
                .credits(15.0)
                .build();
        Product product1 = Product.builder()
                .id("1")
                .name("Satin Dress")
                .price(134.5)
                .description("Modern elegant dress for classy night outs!")
                .sku("gyht78")
                .stock(123)
                .build();
        Product product2 = Product.builder()
                .id("2")
                .name("Silk scarf")
                .price(60.5)
                .description("Colorful handmade silk scarf for classy night outs!")
                .sku("drfg67")
                .stock(80)
                .build();
        FavoriteProduct favoriteProduct1 = new FavoriteProduct();
        favoriteProduct1.setUser(user);
        favoriteProduct1.setProduct(product1);
        FavoriteProduct favoriteProduct2 = new FavoriteProduct();
        favoriteProduct2.setUser(user);
        favoriteProduct2.setProduct(product2);
        List<FavoriteProduct> favoriteProductList = List.of(favoriteProduct1, favoriteProduct2);
        when(favoriteProductRepository.findAllByUserId("1")).thenReturn(favoriteProductList);

        List<Product> expectedProductList = List.of(product1,product2);

        // When
        List<Product> actualFavoriteProductList = favoriteProductService.getProductObjectList(userId);

        // Then
        assertEquals("getProductObjectList() should return only the product objects of the whole list", expectedProductList, actualFavoriteProductList);

    }
}
