package gr.dresso.rest.services;

import gr.dresso.rest.dto.FavoriteProductDTO;
import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FavoriteProductService {
    ResponseEntity<FavoriteProduct> createFavoriteProduct(FavoriteProductDTO favoriteProductDTO);
    ResponseEntity<FavoriteProductDTO> deleteFavoriteProduct(FavoriteProductDTO favoriteProductDTO);
    ResponseEntity<List<Product>> getFavoriteProductsByUser(String userId);
}
