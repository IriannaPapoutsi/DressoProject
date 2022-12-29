package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.FavoriteProductDTO;
import gr.dresso.rest.entities.FavoriteProduct;
import gr.dresso.rest.repositories.FavoriteProductRepository;
import gr.dresso.rest.repositories.ProductRepository;
import gr.dresso.rest.repositories.UserRepository;
import gr.dresso.rest.services.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public FavoriteProduct createFavoriteProductEntityFromFTO(FavoriteProductDTO favoriteProductDTO) {
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setUser(userRepository.findUserById(favoriteProductDTO.getUserId()));
        favoriteProduct.setProduct(productRepository.findProductById(favoriteProductDTO.getProductId()));
        return favoriteProduct;
    }

    @Override
    public ResponseEntity<FavoriteProduct> createFavoriteProduct(FavoriteProductDTO favoriteProductDTO) {
        if (userRepository.existsUserById(favoriteProductDTO.getUserId())
                && productRepository.existsProductById(favoriteProductDTO.getProductId())) {
            System.out.println("Mpainei sto if");
            FavoriteProduct favoriteProduct = createFavoriteProductEntityFromFTO(favoriteProductDTO);
            favoriteProductRepository.save(favoriteProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(favoriteProduct);

        }
        System.out.println("Vgainei ektos if");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity deleteFavoriteProduct(FavoriteProductDTO favoriteProductDTO) {
        if (favoriteProductRepository.existsFavoriteProductByUserIdAndProductId(favoriteProductDTO.getUserId(), favoriteProductDTO.getProductId())) {
            favoriteProductRepository.deleteFavoriteProductByUserIdAndProductId(favoriteProductDTO.getUserId(), favoriteProductDTO.getProductId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
