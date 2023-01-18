package gr.dresso.rest.services;

import gr.dresso.rest.entities.Cart;
import gr.dresso.rest.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface CartService {

    ResponseEntity<Cart> createCartProduct(int userId, int productId);

    /**
     * Method responsible for deleting the whole actual cart related entity which belongs to a user
     * @param userId is the Id of the user's cart to be deleted.
     * @return the status of the successfulness of the cart's deletion.
     */
    ResponseEntity<Void> deleteCart(int userId);

    /**
     * Method responsible for deleting only one specific item of the user's cart
     * @param userId is the Id of the user to delete product from
     * @param productId id the id of the product to be deleted
     * @return the status of the successfulness of the cart item's deletion.
     */
    ResponseEntity<String> deleteCartItem(int userId, int productId);

    /**
     * Method responsible for returning the all of the cart products that belongs to a user
     * @param userId is the Id of the user's cart information needed.
     * @return the total cart items of the user.
     */
    ResponseEntity<List<Product>> getCartByUserId(int userId);

    /**
     * Method responsible for implementing the payment and updating the database based on the successfulness of it.
     * @param  userId is the Id of the user who wants to check out.
     * @return a statement of how the checkout went.
     */
    ResponseEntity<String> checkoutBalance(int userId);


}
