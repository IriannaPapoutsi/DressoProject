package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    boolean existsCartByUserIdAndProductId(int userId, int productId);
    boolean existsCartByUserId(int userId);
    void deleteCartByUserIdAndProductId(int userId, int productId);
    void deleteCartByUserId(int userId);
    List<Cart> findAllByUserId(int userId);
}
