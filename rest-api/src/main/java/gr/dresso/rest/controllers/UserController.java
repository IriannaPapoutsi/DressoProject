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

    // TODO: Make this final
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // TODO: Make sure that user passwords do not appear in the response body because that is sensitive data
    // TODO: For the above, check the @JsonIgnore annotation
    @GetMapping
    public List<User> getAllUsers() {
        // TODO: No need to use "this" before the user service, since there is no other reference with that name
        // TODO: You can also instantly return the method call instead of storing it in a variable, since you do not execute any extra operations on the variable
        List<User> users = this.userService.getAllUsers();
        return users;
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
