package gr.dresso.rest.controllers;

import gr.dresso.rest.dto.CreateUserDTO;
import gr.dresso.rest.dto.UpdateUserDTO;
import gr.dresso.rest.dto.UserLoginDTO;
import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.services.CartService;
import gr.dresso.rest.services.FavoriteProductService;
import gr.dresso.rest.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final FavoriteProductService favoriteProductService;
    private final CartService cartService;

    @Autowired
    public UserController(UserService userService, FavoriteProductService favoriteProductService, CartService cartService){
        this.userService = userService;
        this.favoriteProductService = favoriteProductService;
        this.cartService = cartService;
    }

    @GetMapping
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        return userService.checkUserLogin(userLoginDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable int userId) {
        return userService.deleteUserById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserProfile(@Valid @RequestBody UpdateUserDTO updateUserDTO,
                                                  @PathVariable int userId) {
        return userService.updateUser(updateUserDTO, userId);
    }

    @GetMapping("/{userId}/favorite-products")
    public ResponseEntity<List<Product>> getAllFavoriteProductsByUserId(@PathVariable int userId) {
        return favoriteProductService.getFavoriteProductsByUser(userId);
    }

    @PostMapping("/{userId}/favorite-products/{productId}")
    public ResponseEntity<FavoriteProduct> createFavoriteProduct(@PathVariable int userId, @PathVariable int productId) {
        return favoriteProductService.createFavoriteProduct(userId, productId);
    }

    @DeleteMapping("/{userId}/favorite-products/{productId}")
    public ResponseEntity<String> deleteFavoriteProduct(@PathVariable int userId, @PathVariable int productId) {
        return favoriteProductService.deleteFavoriteProduct(userId, productId);
    }

    @PostMapping("/{userId}/cart-products/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable int userId, @PathVariable int productId) {
        return cartService.createCartProduct(userId, productId);
    }

    @DeleteMapping("/{userId}/cart-products/")
    public ResponseEntity<Void> deleteWholeUserCart(@PathVariable int userId) {
        return cartService.deleteCart(userId);
    }

    @DeleteMapping("/{userId}/cart-products/{producId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable int userId, @PathVariable int producId) {
        return cartService.deleteCartItem(userId, producId);
    }

    @GetMapping("/{userId}/cart-products")
    public ResponseEntity<List<Product>> getProductsFromCartByUserId(@PathVariable int userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("{userId}/checkout")
    public ResponseEntity<String> checkoutBalance(@PathVariable int userId) {
        return cartService.checkoutBalance(userId);
    }

}
