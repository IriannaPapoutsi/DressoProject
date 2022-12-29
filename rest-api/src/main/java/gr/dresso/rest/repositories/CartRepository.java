package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    boolean existsCartByUserIdAndProductId(String userId, String productId);
    void deleteCartByUserIdAndProductId(String userId, String productId);
}
