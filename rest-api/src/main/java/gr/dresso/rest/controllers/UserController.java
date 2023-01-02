package gr.dresso.rest.controllers;

import gr.dresso.rest.dto.CreateUserDTO;
import gr.dresso.rest.dto.UserLoginDTO;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("user")
    public ResponseEntity<User> getUser(@RequestParam String userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        return userService.checkUserLogin(userLoginDTO);
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUserByUserId(@RequestParam String userId) {
        return userService.deleteUserById(userId);
    }

}
