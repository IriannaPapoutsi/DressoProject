package gr.dresso.rest.services;

import gr.dresso.rest.DTO.CreateUserDTO;
import gr.dresso.rest.DTO.UserLoginDTO;
import gr.dresso.rest.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    ResponseEntity<User> createUser(CreateUserDTO createUserDTO);

    ResponseEntity checkUserLogin(UserLoginDTO userLoginDTO);
}
