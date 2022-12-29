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

    public ResponseEntity<Cart> createCart(CartDTO cartDTO) {
        if (userRepository.existsUserById(cartDTO.getUserId())
                && productRepository.existsProductById(cartDTO.getProductId())) {
            Cart cart = createCartEntityFromDTO(cartDTO);
            cartRepository.save(cart);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    public ResponseEntity<Cart> deleteCart(CartDTO cartDTO){
        if (cartRepository.existsCartByUserIdAndProductId(cartDTO.getUserId(), cartDTO.getProductId())) {
            cartRepository.deleteCartByUserIdAndProductId(cartDTO.getUserId(), cartDTO.getProductId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
