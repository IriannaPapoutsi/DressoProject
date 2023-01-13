package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.CartDTO;
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

    Cart createCartEntityFromDTO(CartDTO cartDTO) {
        User user = userRepository.findUserById(cartDTO.getUserId()); // Is this something I should instantly put in setUser()?
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
            // TODO: Use builder, instead of body(null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Cart cart = createCartEntityFromDTO(cartDTO);
        cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    // TODO: Use ResponseEntity<Void>, instead of raw values
    @Override
    public ResponseEntity deleteCart(String userId) {
        if (!cartRepository.existsCartByUserId(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cartRepository.deleteCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // TODO: Use ResponseEntity<Void>, instead of raw values
    @Override
    public ResponseEntity deleteCartItem(CartDTO cartDTO) {
        if (!cartRepository.existsCartByUserIdAndProductId(cartDTO.getUserId(), cartDTO.getProductId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cartRepository.deleteCartByUserIdAndProductId(cartDTO.getUserId(), cartDTO.getProductId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    List<Product> getCartProductsListByUser(String userId) {
        List<Cart> cartList = cartRepository.findAllByUserId(userId);
        return cartList
                .stream()
                // TODO: This can be replaced with method reference (check warning)
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

    double calculateCartCost(String userId) {
        List<Product> cartProducts = getCartProductsListByUser(userId);
        return cartProducts
                .stream()
                // TODO: This can be replaced with method reference (check warning)
                .mapToDouble(productItem -> productItem.getPrice())
                .sum();
    }

    void reduceProductStock(String userId) {
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
        // TODO: I believe that calling a method that has actually been created to be called from the Controller is a bad idea
        // TODO: I would extract the logic that I need in another method, and call that from both deleteCart() and checkoutBalance()
        // TODO: Response entities bodies is not something that a user sees, so there is no need to make it look so "nice". Typically its status updates (you can include numbers like the remaining credits), or resources (like entities of the database).
        deleteCart(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Checkout successfully made! Your remained credits are now "
                + user.getCredits() + " :)");
    }
}
