package gr.dresso.rest.services.impl;

import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.entities.Product;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.repositories.FavoriteProductRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.repositories.UserRepository;
import gr.dresso.rest.services.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public FavoriteProduct createFavoriteProductEntity(int userId, int productId) {
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        Optional<User> userResponse = userRepository.findById(userId);
        User user = userResponse.get();
        favoriteProduct.setUser(user);
        Optional<Product> productResponse = productRepository.findById(productId);
        Product product = productResponse.get();
        favoriteProduct.setProduct(product);
        return favoriteProduct;
    }

    @Override
    public ResponseEntity<FavoriteProduct> createFavoriteProduct(int userId, int productId) {
        if (userRepository.existsById(userId)
                && productRepository.existsById(productId)
                && !favoriteProductRepository.existsFavoriteProductByUserIdAndProductId(userId, productId)) {
            FavoriteProduct favoriteProduct = createFavoriteProductEntity(userId, productId);
            favoriteProductRepository.save(favoriteProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(favoriteProduct);

        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Override
    public ResponseEntity<String> deleteFavoriteProduct(int userId, int productId) {
        if (favoriteProductRepository
                .existsFavoriteProductByUserIdAndProductId(userId, productId)) {
            favoriteProductRepository
                    .deleteFavoriteProductByUserIdAndProductId(userId, productId);
            return ResponseEntity.status(HttpStatus.OK).body("Favorite Product successfully deleted!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    List<Product> getProductObjectList(int userId) {
        List<FavoriteProduct> favoriteProductList = favoriteProductRepository.findAllByUserId(userId);
        return favoriteProductList
                .stream()
                .map(FavoriteProduct::getProduct)
                .toList();
    }

    @Override
    public ResponseEntity<List<Product>> getFavoriteProductsByUser(int userId) {
        if (!favoriteProductRepository.existsFavoriteProductByUserId(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(getProductObjectList(userId));
    }
}
