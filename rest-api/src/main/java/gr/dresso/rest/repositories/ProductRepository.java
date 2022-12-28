package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.Category;
import gr.dresso.rest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByName(String name);

    List<Product> findAllByCategoryName(String categoryName);
    List<Product> findAllByNameAndCategoryName(String name, String category);
}
