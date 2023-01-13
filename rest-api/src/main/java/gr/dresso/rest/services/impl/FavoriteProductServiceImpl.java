package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.FavoriteProductDTO;
import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.repositories.FavoriteProductRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.repositories.UserRepository;
import gr.dresso.rest.services.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteProductServiceImpl implements FavoriteProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavoriteProductRepository favoriteProductRepository;

    @Autowired
    public FavoriteProductServiceImpl(UserRepository userRepository,
                                      ProductRepository productRepository, FavoriteProductRepository favoriteProductRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.favoriteProductRepository = favoriteProductRepository;
    }

    public FavoriteProduct createFavoriteProductEntityFromDTO(FavoriteProductDTO favoriteProductDTO) {
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setUser(userRepository.findUserById(favoriteProductDTO.getUserId()));
        favoriteProduct.setProduct(productRepository.findProductById(favoriteProductDTO.getProductId()));
        return favoriteProduct;
    }

    @Override
    public ResponseEntity<FavoriteProduct> createFavoriteProduct(FavoriteProductDTO favoriteProductDTO) {
        if (userRepository.existsUserById(favoriteProductDTO.getUserId())
                && productRepository.existsProductById(favoriteProductDTO.getProductId())) {
            FavoriteProduct favoriteProduct = createFavoriteProductEntityFromDTO(favoriteProductDTO);
            favoriteProductRepository.save(favoriteProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(favoriteProduct);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // TODO: Use ResponseEntity<Void>, instead of raw values
    @Override
    public ResponseEntity deleteFavoriteProduct(FavoriteProductDTO favoriteProductDTO) {
        // TODO: Lines are too long
        if (favoriteProductRepository.existsFavoriteProductByUserIdAndProductId(favoriteProductDTO.getUserId(), favoriteProductDTO.getProductId())) {
            favoriteProductRepository.deleteFavoriteProductByUserIdAndProductId(favoriteProductDTO.getUserId(), favoriteProductDTO.getProductId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    List<Product> getProductObjectList(String userId) {
        List<FavoriteProduct> favoriteProductList = favoriteProductRepository.findAllByUserId(userId);
        return favoriteProductList
                .stream()
                // TODO: Method reference may be used, check warning
                .map(x -> x.getProduct())
                .toList();
    }

    @Override
    public ResponseEntity<List<Product>> getFavoriteProductsByUser(String userId) {
        if (!favoriteProductRepository.existsFavoriteProductByUserId(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(getProductObjectList(userId));
    }
}
