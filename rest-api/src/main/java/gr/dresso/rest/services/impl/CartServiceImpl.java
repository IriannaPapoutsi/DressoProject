package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.CartDTO;
import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.repositories.CartRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.repositories.UserRepository;
import gr.dresso.rest.services.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Cart createCartEntityFromDTO(CartDTO cartDTO) {
        User user = userRepository.findUserById(cartDTO.getUserId()); // Is this something I should instantly pu in setUser()?
        Product product = productRepository.findProductById(cartDTO.getProductId());
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        return cart;
    }

    @Override
    public ResponseEntity<Cart> createCart(CartDTO cartDTO) {
        if (!userRepository.existsUserById(cartDTO.getUserId())
                || !productRepository.existsProductById(cartDTO.getProductId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Cart cart = createCartEntityFromDTO(cartDTO);
        cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @Override
    public ResponseEntity deleteCart(String userId) {
        if (!cartRepository.existsCartByUserId(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cartRepository.deleteCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity deleteCartItem(CartDTO cartDTO) {
        if (!cartRepository.existsCartByUserIdAndProductId(cartDTO.getUserId(), cartDTO.getProductId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cartRepository.deleteCartByUserIdAndProductId(cartDTO.getUserId(), cartDTO.getProductId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private List<Product> getCartProductsListByUser(String userId) {
        List<Cart> cartList = cartRepository.findAllByUserId(userId);
        return cartList
                .stream()
                .map(cartItem -> cartItem.getProduct())
                .toList();

    }

    @Override
    public ResponseEntity<List<Product>> getCartByUserId(String userId) {
        if (!userRepository.existsUserById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Product> cartProductList = getCartProductsListByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartProductList);
    }

    private double calculateCartCost(String userId) {
        List<Product> cartProducts = getCartProductsListByUser(userId);
        return cartProducts
                .stream()
                .mapToDouble(productItem -> productItem.getPrice())
                .sum();
    }

    private void reduceProductStock(String userId) {
        List<Product> cartProductList = getCartProductsListByUser(userId);
        cartProductList.forEach(x -> {
            x.setStock(x.getStock() - 1);
            productRepository.save(x);
        });
    }

    private void reduceUserCredits(User user, double totalCost){
        double remainedCredits = user.getCredits() - totalCost;
        user.setCredits(Math.round(remainedCredits * 100.0) / 100.0);
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<String> checkoutBalance(String userId) {
        if (!userRepository.existsUserById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        double totalCost = calculateCartCost(userId);
        User user = userRepository.findUserById(userId);
        double credits = user.getCredits();
        if (credits < totalCost) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sorry, total cost is " + totalCost
                    + " and you only have " + credits + " credits :/");
        }
        reduceUserCredits(user, totalCost);
        reduceProductStock(userId);
        deleteCart(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Checkout successfully made! Your remained credits are now "
        + user.getCredits() + " :)");
    }
}
