package gr.dresso.rest.services;

import gr.dresso.rest.CreateUserDTO;
import gr.dresso.rest.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    ResponseEntity<User> createUser(CreateUserDTO createUserDTO);
}
