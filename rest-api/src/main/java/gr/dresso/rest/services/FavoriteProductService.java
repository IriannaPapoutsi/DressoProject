package gr.dresso.rest.services;

import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FavoriteProductService {
    ResponseEntity<FavoriteProduct> createFavoriteProduct(int userId, int productId);
    ResponseEntity<String> deleteFavoriteProduct(int userId, int productId);
    ResponseEntity<List<Product>> getFavoriteProductsByUser(int userId);
}
