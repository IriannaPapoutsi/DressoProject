package gr.dresso.rest.controllers;

import gr.dresso.rest.dto.FavoriteProductDTO;
import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.services.FavoriteProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/favorite-product")
public class FavoriteProductController {
    private final FavoriteProductService favoriteProductService;

    public FavoriteProductController(FavoriteProductService favoriteProductService) {
        this.favoriteProductService = favoriteProductService;
    }

    @PostMapping
    public ResponseEntity<FavoriteProduct> createFavoriteProduct(@RequestBody FavoriteProductDTO favoriteProductDTO) {
        return favoriteProductService.createFavoriteProduct(favoriteProductDTO);
    }
    @Transactional
    @DeleteMapping
    public ResponseEntity<FavoriteProductDTO> deleteFavoriteProduct(@RequestBody FavoriteProductDTO favoriteProductDTO) {
        return favoriteProductService.deleteFavoriteProduct(favoriteProductDTO);
    }
}
