package gr.dresso.rest.services;

import gr.dresso.rest.dto.CartDTO;
import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface CartService {
    /**
     * Method responsible for transforming a Data Transfer Object to actual cart related entities and then
     * persisting them to the database.
     * @param cartDTO is the Data Transfer Object with the to-be-created cart information.
     * @return the created and persisted cart instance.
     */
    ResponseEntity<Cart> createCart(CartDTO cartDTO);

    /**
     * Method responsible for deleting the whole actual cart related entity which belongs to a user
     * @param userId is the Id of the user's cart to be deleted.
     * @return the status of the successfulness of the cart's deletion.
     */
    ResponseEntity<Cart> deleteCart(String userId);

    /**
     * Method responsible for deleting only one specific item of the user's cart
     * @param cartDTO – is the Data Transfer Object with the to-be-deleted cart item information.
     * @return the status of the successfulness of the cart item's deletion.
     */
    ResponseEntity<Cart> deleteCartItem(CartDTO cartDTO);

    /**
     * Method responsible for returning the all of the cart products that belongs to a user
     * @param userId is the Id of the user's cart information needed.
     * @return the total cart items of the user.
     */
    ResponseEntity<List<Product>> getCartByUserId(String userId);

    /**
     * Method responsible for implementing the payment and updating the database based on the successfulness of it.
     * @param  userId is the Id of the user who wants to check out.
     * @return a statement of how the checkout went.
     */
    ResponseEntity<String> checkoutBalance(String userId);


}
