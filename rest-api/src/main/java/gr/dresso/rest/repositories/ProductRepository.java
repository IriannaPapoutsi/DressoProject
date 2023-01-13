package gr.dresso.rest.repositories;

// TODO: Remove redundant imports
import gr.dresso.rest.entities.Category;
import gr.dresso.rest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // TODO: Newline here
    // TODO: I would change this to findAllByNameContains, so that we dont actually have to provide the name, but also a part of it is okay (for search)
    List<Product> findAllByName(String name);
    List<Product> findAllByCategoryName(String categoryName);
    List<Product> findAllByNameAndCategoryName(String name, String category);
    boolean existsProductById(String id);
    // TODO: This method does not need to be created, it exists on the default methods of the repository (findById)
    Product findProductById(String id);
    // TODO: Newline here
}
