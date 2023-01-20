package gr.dresso.rest.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// TODO: Add @DirtiesContext to make Spring reset your database after this test class is executed.
// TODO: After each test class is executed, the database scripts will be run again and if the database is not reset with @DirtiesContext
// TODO: then it will create conflicts and the test will fail
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ColorRepositoryTests {

    @Autowired
    private ColorRepository colorRepository;

    // TODO: I would rename this method to findAll_shouldNotThrowException to follow the conventions we have made for our tests
    @Test
    public void quickTest() {
        colorRepository.findAll();
    }

}
