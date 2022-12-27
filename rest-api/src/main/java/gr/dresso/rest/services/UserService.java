package gr.dresso.rest.services;

import gr.dresso.rest.DTO.CreateUserDTO;
import gr.dresso.rest.DTO.UserLoginDTO;
import gr.dresso.rest.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    // TODO: Generally, it is a good idea to document your code with Javadoc, check this example
    // TODO: You can document classes, interfaces, methods, or even fields (no need to spam everything, just where needed)
    /**
     * Method responsible for transforming a Data Transfer Object to actual user related entities and then
     * persisting them to the database.
     * @param createUserDTO is the Data Transfer Object with the to-be-created user information.
     * @return the created and persisted user instance.
     */
    ResponseEntity<User> createUser(CreateUserDTO createUserDTO);

    ResponseEntity checkUserLogin(UserLoginDTO userLoginDTO);
}
