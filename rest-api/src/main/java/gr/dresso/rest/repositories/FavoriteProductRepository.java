package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer>{
    boolean existsFavoriteProductByUserId(String userId);
    boolean existsFavoriteProductByUserIdAndProductId(String userId, String productId);
    void deleteFavoriteProductByUserIdAndProductId(String userId, String productId);
    List<FavoriteProduct> findAllByUserId(String userId);
}
