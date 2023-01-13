package gr.dresso.rest.controllers;

import gr.dresso.rest.dto.CartDTO;
import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.services.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
// TODO: I would make this plural, the same way you have made the other base endpoints
@RequestMapping("api/cart")
public class CartController {
    // TODO: Newline here
    private final CartService cartService;
    // TODO: Newline here
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // TODO: I would rename all these methods to addProductToCart, deleteProductFromCart etc.
    // TODO: @Valid missing here
    // TODO: I would move this to the User controller and make it /api/users/{userId}/cart-products/{productId}
    // TODO: Should this method check if cart record already exists for user id + cart id?
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cartDTO) {
        return cartService.createCart(cartDTO);
    }

    // TODO: I would not use a request parameter here
    // TODO: I would move this to the User controller and make it /api/users/{userId}/carts/{cartId}
    @DeleteMapping
    public ResponseEntity<Cart> deleteCart(@RequestParam String userId) {
        return cartService.deleteCart(userId);
    }

    // TODO: I would move this to the User controller and make it /api/users/{userId}/cart-products/{productId}
    // TODO: @Valid missing here
    @DeleteMapping("item")
    public ResponseEntity<Cart> deleteCartItem(@RequestBody CartDTO cartDTO) {
        return cartService.deleteCartItem(cartDTO);
    }

    // TODO: I would move this to the User controller and make it /api/users/{userId}/cart-products
    @GetMapping
    public ResponseEntity<List<Product>> getCartProductsByUserId(@RequestParam String userId) {
        return cartService.getCartByUserId(userId);
    }

    // TODO: I would move this to the User controller as: api/v1/users/{userId}/checkout
    @PostMapping("checkout")
    public ResponseEntity<String> checkoutBalance(@RequestParam String userId) {
        return cartService.checkoutBalance(userId);
    }

}
