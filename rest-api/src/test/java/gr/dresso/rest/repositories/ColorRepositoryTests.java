package gr.dresso.rest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ColorRepositoryTests {

    @Autowired
    private ColorRepository colorRepository;

    @Test
    public void findAll_shouldNotThrowException() {
        colorRepository.findAll();
    }

}
