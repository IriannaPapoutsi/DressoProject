package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.CartDTO;
import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.repositories.CartRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)

public class CartServiceImplTests {

    private Mock mock;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private List<Cart> mockCartList() {
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
        Cart cartProduct1 = new Cart();
        cartProduct1.setUser(user);
        cartProduct1.setProduct(product1);
        Cart cartProduct2 = new Cart();
        cartProduct2.setUser(user);
        cartProduct2.setProduct(product2);
        return List.of(cartProduct1, cartProduct2);
    }

    private List<Product> mockProductList() {
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
        return List.of(product1, product2);
    }

    @Test
    public void createCartEntityFromDTO_shouldReturnCreatedCartObject() {
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
        Product product = Product.builder()
                .id("1")
                .name("Satin Dress")
                .price(134.5)
                .description("Modern elegant dress for classy night outs!")
                .sku("gyht78")
                .stock(123)
                .build();
        when(userRepository.findUserById("1")).thenReturn(user);
        when(productRepository.findProductById("1")).thenReturn(product);
        CartDTO cartDTO = new CartDTO("1", "1");
        Cart expectedCart = new Cart();
        expectedCart.setUser(user);
        expectedCart.setProduct(product);

        // When
        Cart actualCart = cartService.createCartEntityFromDTO(cartDTO);

        // Then
        assertEquals("createCartEntityFromDTO() should return the created cart object", expectedCart, actualCart);
    }

    @Test
    public void getCartProductsListByUser_shouldReturnOnlyProductsOfUser() {
        // Given
        String userId = "1";
        List<Cart> cartList = mockCartList();
        when(cartRepository.findAllByUserId("1")).thenReturn(cartList);

        List<Product> expectedProductList = mockProductList();

        // When
        List<Product> actualProductList = cartService.getCartProductsListByUser(userId);

        // Then
        assertEquals("getCartProductsListByUser() should return only the product objects of the whole cart list", expectedProductList, actualProductList);
    }

    @Test
    public void calculateCartCost_shouldReturnTheTotalCostOfCart() {
        // Given
        String userId = "1";
        List<Cart> cartList = mockCartList();
        when(cartRepository.findAllByUserId("1")).thenReturn(cartList);
        double expectedCost = 195.0;

        // When
        double actualCartCost = cartService.calculateCartCost(userId);

        // Then
        assertEquals("calculateCartCost() should return the total cost of cart products", expectedCost, actualCartCost);
    }

    @Test
    public void reduceProductStock_shouldReduceProductStockBaseOnCart() {
        // Given
        String userId = "1";
        List<Cart> cartList = mockCartList();
        when(cartRepository.findAllByUserId("1")).thenReturn(cartList);
        List<Product> productList = mockProductList();
        int expectedStockList[] = {122, 79};

        // When
        when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        cartService.reduceProductStock(userId);

        // Then
        //assertEquals("reduceProductStock() should reduce the stock of a product based on cart", expectedStockList[0], 0) );
    }

    @Test
    public void reduceUserCredits() {
        // Given
        User user = User.builder()
                .id("1")
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("IT456")
                .country("Italy")
                .city("Turin, San Salvario")
                .address("Via Po 23")
                .email("marpap@gmail.com")
                .credits(150.0)
                .build();
        double totalCost = 100.0;
        double expectedRemainedCredits = 50.0;

        // When
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        cartService.reduceUserCredits(user, totalCost);

        // Then
        assertEquals("reduceUserCredits() should reduce the credits of the user based on total cost", expectedRemainedCredits, user.getCredits());
    }
}
