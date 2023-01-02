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
@RequestMapping("api/cart")
public class CartController {
    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cartDTO) {
        return cartService.createCart(cartDTO);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<Cart> deleteCart(@RequestBody CartDTO cartDTO) {
        return cartService.deleteCart(cartDTO);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getCartProductsByUserId(@RequestParam String userId) {
        return cartService.getCartByUserId(userId);
    }
}
