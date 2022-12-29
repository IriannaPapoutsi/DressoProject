package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer>{
    boolean existsFavoriteProductByUserIdAndProductId(String userId, String productId);
    void deleteFavoriteProductByUserIdAndProductId(String userId, String productId);
}
