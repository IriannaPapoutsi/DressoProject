package gr.dresso.rest.services.impl;

import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.repositories.CartRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.repositories.UserRepository;
import gr.dresso.rest.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    Cart createCartEntity(User user, Product product) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        return cart;
    }

    @Override
    public ResponseEntity<Cart> createCartProduct(int userId, int productId) {
        Optional<User> userResponse = userRepository.findById(userId);
        Optional<Product> productResponse = productRepository.findById(productId);
        boolean shouldNotCreateCartItem = userResponse.isEmpty() || productResponse.isEmpty();
        if (shouldNotCreateCartItem) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userResponse.get();
        Product product = productResponse.get();
        Cart cart = createCartEntity(user, product);
        cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    private ResponseEntity<Void> clearUserCart(int userId) {
        if (!cartRepository.existsCartByUserId(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cartRepository.deleteCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> deleteCart(int userId) {
        return clearUserCart(userId);
    }

    @Override
    public ResponseEntity<String> deleteCartItem(int userId, int productId) {
        if (!cartRepository.existsCartByUserIdAndProductId(userId, productId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cartRepository.deleteCartByUserIdAndProductId(userId, productId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted cart item with user id: "
                + userId + " and product id: " + productId);
    }

    List<Product> getCartProductsListByUser(int userId) {
        List<Cart> cartList = cartRepository.findAllByUserId(userId);
        return cartList
                .stream()
                .map(Cart::getProduct)
                .toList();
    }

    @Override
    public ResponseEntity<List<Product>> getCartByUserId(int userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Product> cartProductList = getCartProductsListByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartProductList);
    }

    double calculateCartCost(int userId) {
        List<Product> cartProducts = getCartProductsListByUser(userId);
        return cartProducts
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    void reduceProductStock(int userId) {
        List<Product> cartProductList = getCartProductsListByUser(userId);
        cartProductList.forEach(x -> {
            x.setStock(x.getStock() - 1);
            productRepository.save(x);
        });
    }

    void reduceUserCredits(User user, double totalCost) {
        double remainedCredits = user.getCredits() - totalCost;
        user.setCredits(Math.round(remainedCredits * 100.0) / 100.0);
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<String> checkoutBalance(int userId) {
        Optional<User> userResponse = userRepository.findById(userId);
        if (userResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        double totalCost = calculateCartCost(userId);
        User user = userResponse.get();
        double credits = user.getCredits();
        if (credits < totalCost) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Total cost: " + totalCost
                    + " user credits: " + credits);
        }
        reduceUserCredits(user, totalCost);
        reduceProductStock(userId);
        clearUserCart(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User credits left: "
                + user.getCredits());
    }
}
