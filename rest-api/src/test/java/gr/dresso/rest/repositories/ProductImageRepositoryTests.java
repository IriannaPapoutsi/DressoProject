package gr.dresso.rest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductImageRepositoryTests {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Test
    public void findAll_shouldNotThrowException() {
        productImageRepository.findAll();
    }

}
