package gr.dresso.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

// @DirtiesContext make Spring reset your database after this test class is executed.
// After each test class is executed, the database scripts will be run again and if the database is not reset with @DirtiesContext
// then it will create conflicts and the test will fail
@SpringBootTest
class RestApplicationTests {

	@Test
	void contextLoads() {
	}

}
