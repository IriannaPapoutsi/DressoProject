package gr.dresso.rest.services.impl;

import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.repositories.CartRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)

public class CartServiceImplTests {

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
                .id(1)
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
                .id(1)
                .name("Satin Dress")
                .price(134.5)
                .description("Modern elegant dress for classy night outs!")
                .sku("gyht78")
                .stock(123)
                .build();
        Product product2 = Product.builder()
                .id(2)
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
                .id(1)
                .name("Satin Dress")
                .price(134.5)
                .description("Modern elegant dress for classy night outs!")
                .sku("gyht78")
                .stock(123)
                .build();
        Product product2 = Product.builder()
                .id(2)
                .name("Silk scarf")
                .price(60.5)
                .description("Colorful handmade silk scarf for classy night outs!")
                .sku("drfg67")
                .stock(80)
                .build();
        return List.of(product1, product2);
    }

    @Test
    public void createCartEntity_shouldReturnCreatedCartObject() {
        // Given
        User user = User.builder()
                .id(1)
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
                .id(1)
                .name("Satin Dress")
                .price(134.5)
                .description("Modern elegant dress for classy night outs!")
                .sku("gyht78")
                .stock(123)
                .build();
        Cart expectedCart = new Cart();
        expectedCart.setUser(user);
        expectedCart.setProduct(product);

        // When
        Cart actualCart = cartService.createCartEntity(user, product);

        // Then
        assertThat(actualCart).usingRecursiveComparison().isEqualTo(expectedCart);
    }

    @Test
    public void getCartProductsListByUser_shouldReturnOnlyProductsOfUser() {
        // Given
        int userId = 1;
        List<Cart> cartList = mockCartList();
        when(cartRepository.findAllByUserId(1)).thenReturn(cartList);

        List<Product> expectedProductList = mockProductList();

        // When
        List<Product> actualProductList = cartService.getCartProductsListByUser(userId);

        // Then
        assertThat(actualProductList).usingRecursiveComparison().isEqualTo(expectedProductList);
    }

    @Test
    public void calculateCartCost_shouldReturnTheTotalCostOfCart() {
        // Given
        int userId = 1;
        List<Cart> cartList = mockCartList();
        when(cartRepository.findAllByUserId(1)).thenReturn(cartList);
        double expectedCost = 195.0;

        // When
        double actualCartCost = cartService.calculateCartCost(userId);

        // Then
        assertEquals("calculateCartCost() should return the total cost of cart products", expectedCost, actualCartCost);
    }

    @Test
    public void reduceProductStock_shouldReduceProductStockBasedOnCartCheckout() {
        // Given
        int userId = 1;
        List<Cart> cartList = mockCartList();
        when(cartRepository.findAllByUserId(1)).thenReturn(cartList);
        // TODO: Rename this to expectedStocks (it's an array, not a list)
        int expectedStockList[] = {122, 79};

        // When
        // TODO: when() statements should be placed on the // Given section
        // TODO: The when() statement below is redundant
        when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        cartService.reduceProductStock(userId);

        // Then
        // TODO: You can use the following to verify that the stocks of the products have been reduced and that the save method of the
        // TODO: repository has been called for each product
//        assertEquals("Message", expectedStockList[0], cartList.get(0).getProduct().getStock());
//        assertEquals("Message", expectedStockList[1], cartList.get(1).getProduct().getStock());
//        verify(productRepository).save(cartList.get(0).getProduct());
//        verify(productRepository).save(cartList.get(1).getProduct());
    }

    @Test
    public void reduceUserCredits() {
        // Given
        User user = User.builder()
                .id(1)
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
        // TODO: When statements should be placed on the // Given section
        // TODO: This when statement is redundant since you do not use the value that is returned by userRepository.save anywhere
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        cartService.reduceUserCredits(user, totalCost);

        // Then
        // TODO: I would also verify() that userRepository.save was called
        assertEquals("reduceUserCredits() should reduce the credits of the user based on total cost",
                expectedRemainedCredits, user.getCredits());
    }
}
