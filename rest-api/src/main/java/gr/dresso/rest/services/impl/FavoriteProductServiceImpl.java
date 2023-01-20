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

    // TODO: Pass the actual user and products on this method, not the Optionals
    public FavoriteProduct createFavoriteProductEntity(Optional<User> userResponse, Optional<Product> productResponse) {
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        User user = userResponse.get();
        favoriteProduct.setUser(user);
        Product product = productResponse.get();
        favoriteProduct.setProduct(product);
        return favoriteProduct;
    }

    @Override
    public ResponseEntity<FavoriteProduct> createFavoriteProduct(int userId, int productId) {
        Optional<User> userResponse = userRepository.findById(userId);
        Optional<Product> productResponse = productRepository.findById(productId);
        boolean userExists = userResponse.isPresent();
        boolean productExists = productResponse.isPresent();
        // TODO: I would move the !userExists || !productExists check here
        boolean userAndProductExist = userExists && productExists;
        boolean favoriteProductAlreadyExists =
                favoriteProductRepository.existsFavoriteProductByUserIdAndProductId(userId, productId);
        // TODO: If you move the !userExists || !productExists check above, then here you can check for favoriteProductAlreadyExists
        //  without userAndProductExist check (since if it was false then a value would have already been returned)
        //  If the value of that is true, then return CONFLICT, otherwise return CREATED like you do.
        boolean shouldCreateFavoriteProduct = userAndProductExist
                && !favoriteProductAlreadyExists;
        if (shouldCreateFavoriteProduct) {
            FavoriteProduct favoriteProduct = createFavoriteProductEntity(userResponse, productResponse);
            favoriteProductRepository.save(favoriteProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(favoriteProduct);
        }
        if (!userExists || !productExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (favoriteProductAlreadyExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // TODO: Remove this comment and do not return NULL in any case
        //return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return null;
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

    List<Product> getUserFavoriteProducts(int userId) {
        List<FavoriteProduct> favoriteProductList = favoriteProductRepository.findAllByUserId(userId);
        return favoriteProductList
                .stream()
                .map(FavoriteProduct::getProduct)
                .toList();
    }

    @Override
    public ResponseEntity<List<Product>> getFavoriteProductsByUser(int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(getUserFavoriteProducts(userId));
    }
}
