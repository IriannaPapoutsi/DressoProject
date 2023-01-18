package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByNameContains(String name);
    List<Product> findAllByCategoryNameContains(String categoryName);
    List<Product> findAllByNameContainsAndCategoryNameContains(String name, String category);

}
