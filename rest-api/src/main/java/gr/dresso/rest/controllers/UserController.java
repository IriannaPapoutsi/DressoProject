package gr.dresso.rest.controllers;

import gr.dresso.rest.DTO.CreateUserDTO;
import gr.dresso.rest.DTO.UserLoginDTO;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;


    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return users;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        return userService.checkUserLogin(userLoginDTO);
    }

}
