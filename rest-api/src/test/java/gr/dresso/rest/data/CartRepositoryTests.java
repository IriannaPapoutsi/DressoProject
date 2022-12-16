package gr.dresso.rest.data;

import gr.dresso.rest.repositories.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void quickTest() {
        cartRepository.findAll();
    }

}
