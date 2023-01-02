package gr.dresso.rest.services;

import gr.dresso.rest.dto.CartDTO;
import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    /**
     * Method responsible for transforming a Data Transfer Object to actual cart related entities and then
     * persisting them to the database.
     * @param cartDTO is the Data Transfer Object with the to-be-created cart information.
     * @return the created and persisted cart instance.
     */
    ResponseEntity<Cart> createCart(CartDTO cartDTO);
    /**
     * Method responsible for deleteing the actual cart related entities via  and then
     * persisting them to the database.
     * @param cartDTO is the Data Transfer Object with the to-be-created cart information.
     * @return the created and persisted cart instance.
     */
    ResponseEntity<Cart> deleteCart(CartDTO cartDTO);
    ResponseEntity<List<Product>> getCartByUserId(String userId);
}
