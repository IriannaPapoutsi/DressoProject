package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    boolean existsCartByUserIdAndProductId(String userId, String productId);
    boolean existsCartByUserId(String userId);
    void deleteCartByUserIdAndProductId(String userId, String productId);
    void deleteCartByUserId(String userId);
    List<Cart> findAllByUserId(String userId);
}
