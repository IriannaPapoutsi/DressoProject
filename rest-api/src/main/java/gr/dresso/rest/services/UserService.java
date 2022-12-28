package gr.dresso.rest.services;

import gr.dresso.rest.DTO.CreateUserDTO;
import gr.dresso.rest.DTO.UserLoginDTO;
import gr.dresso.rest.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    List<User> getAllUsers();

    /**
     * Method responsible for transforming a Data Transfer Object to actual user related entities and then
     * persisting them to the database.
     * @param createUserDTO is the Data Transfer Object with the to-be-created user information.
     * @return the created and persisted user instance.
     */
    ResponseEntity<User> createUser(CreateUserDTO createUserDTO);

    /**
     * Method responsible for transforming a Data Transfer Object to actual userLogin related entities to check
     * if they already exists in the database.
     * @param userLoginDTO is the Data Transfer Object with the user credentials to be checked.
     * @return the status of whether the credentials are confirmed or not.
     */
    ResponseEntity checkUserLogin(UserLoginDTO userLoginDTO);
}
