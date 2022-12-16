package gr.dresso.rest.data;

import gr.dresso.rest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void quickTest() {
        userRepository.findAll();
    }

}
