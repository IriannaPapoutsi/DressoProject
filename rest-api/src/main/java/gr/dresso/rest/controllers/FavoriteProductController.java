package gr.dresso.rest.controllers;

import gr.dresso.rest.dto.FavoriteProductDTO;
import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.services.FavoriteProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
// TODO: I would make this plural, the same way you have made the other endpoints
@RequestMapping("api/favorite-product")
public class FavoriteProductController {
    private final FavoriteProductService favoriteProductService;

    // TODO: @Autowired missing
    public FavoriteProductController(FavoriteProductService favoriteProductService) {
        this.favoriteProductService = favoriteProductService;
    }

    // TODO: I would put this endpoint in the User controller, as: api/users/{userId}/favorite-products
    @GetMapping
    public ResponseEntity<List<Product>> getAllFavoriteProductsByUserId(@RequestParam String userId) {
        return favoriteProductService.getFavoriteProductsByUser(userId);
    }

    // TODO: @Valid missing here before @RequestBody
    // TODO: I would put this endpoint in the User controller, as: api/users/{userId}/favorite-products/{productId}
    // TODO: This endpoint does not check if a favorite already exists before creating it, which is a bug
    @PostMapping
    public ResponseEntity<FavoriteProduct> createFavoriteProduct(@RequestBody FavoriteProductDTO favoriteProductDTO) {
        return favoriteProductService.createFavoriteProduct(favoriteProductDTO);
    }

    // TODO: @Valid missing here before @RequestBody
    // TODO: I would put this endpoint in the User controller, as: api/users/{userId}/favorite-products/{productId}
    @DeleteMapping
    public ResponseEntity<FavoriteProductDTO> deleteFavoriteProduct(@RequestBody FavoriteProductDTO favoriteProductDTO) {
        return favoriteProductService.deleteFavoriteProduct(favoriteProductDTO);
    }

}
