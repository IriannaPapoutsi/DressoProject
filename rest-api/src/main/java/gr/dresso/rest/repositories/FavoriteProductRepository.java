package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer>{
    boolean existsFavoriteProductByUserId(int userId);
    boolean existsFavoriteProductByUserIdAndProductId(int userId, int productId);
    void deleteFavoriteProductByUserIdAndProductId(int userId, int productId);
    List<FavoriteProduct> findAllByUserId(int userId);
}
