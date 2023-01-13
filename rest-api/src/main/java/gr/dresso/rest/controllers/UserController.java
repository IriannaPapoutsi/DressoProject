package gr.dresso.rest.controllers;

import gr.dresso.rest.dto.CreateUserDTO;
import gr.dresso.rest.dto.UpdateUserDTO;
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
    // TODO: Newline here
    private final UserService userService;
    // TODO: Newline here
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    // TODO: Endpoint names should not use the same term twice and with different tenses (singular / plural)
    // TODO: For this kind of cause, you also should NOT use a Request Parameter (that is used for searching / filtering mostly)
    // TODO: A good design would be to get a specific user via an endpoint like: /api/users/5 -> 5 here is the id of the user
    // TODO: This is called path variable, check how it is implemented with Spring Boot
    @GetMapping("/user")
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

    // TODO: Similarly to before, the user id here should be part of the path (Path Variable), not a request parameter
    // TODO: e.g. /api/v1/users/3
    // TODO: This endpoint is broken because you use @Data on JPA entities. If you open the JPA entities, you will see a warning.
    // TODO: To fix this, use @Getter and @Setter, instead of @Data which creates a problematic equals() / hashcode()
    // TODO: Also, for Delete endpoints, people typically return only the status code (or a status message as the body, but not the whole resource).
    // TODO: The above is not a strict rule, but you may want to follow it
    @DeleteMapping
    public ResponseEntity<User> deleteUserByUserId(@RequestParam String userId) {
        return userService.deleteUserById(userId);
    }

    // TODO: Again, the design of the endpoint could be improved
    // TODO: Use something similar to before, e.g. /api/v1/users/3
    // TODO: Also, the next line is too long
    @PutMapping
    public ResponseEntity<User> updateUserProfile(@Valid @RequestBody UpdateUserDTO updateUserDTO, @RequestParam String userId) {
        return userService.updateUser(updateUserDTO, userId);
    }

}
