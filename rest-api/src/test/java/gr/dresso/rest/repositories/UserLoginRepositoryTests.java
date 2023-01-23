package gr.dresso.rest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserLoginRepositoryTests {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Test
    public void findAll_shouldNotThrowException() {
        userLoginRepository.findAll();
    }

}
