package gr.dresso.rest.controllers;

import gr.dresso.rest.dto.CreateUserDTO;
import gr.dresso.rest.dto.UserLoginDTO;
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
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    // TODO: Make this return ResponseEntity<Void>, check: https://stevenschwenke.de/ReturningAnEmptyResponseEntityInSpringMVC
    // TODO: You can also return it as ResponseEntity.status(<X>).build(), instead of using the new keyword
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        return userService.checkUserLogin(userLoginDTO);
    }

}
