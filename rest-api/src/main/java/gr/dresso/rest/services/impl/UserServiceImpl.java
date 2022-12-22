package gr.dresso.rest.services.impl;

import gr.dresso.rest.CreateUserDTO;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.entities.UserLogin;
import gr.dresso.rest.repositories.UserLoginRepository;
import gr.dresso.rest.repositories.UserRepository;
import gr.dresso.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }



    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    private User createUserEntityFromDTO(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setPostalCode(createUserDTO.getPostalCode());
        user.setCountry(createUserDTO.getCountry());
        user.setCity(createUserDTO.getCity());
        user.setAddress(createUserDTO.getAddress());
        user.setEmail(createUserDTO.getEmail());
        user.setCredits((createUserDTO.getCredits()));
        return user;
    }

    private UserLogin createUserLoginEntityFromDTO(User user, CreateUserDTO createUserDTO) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUser(user);
        userLogin.setUsername(createUserDTO.getUsername());
        userLogin.setPassword(createUserDTO.getPassword());
        return userLogin;
    }

    @Override
    public ResponseEntity<User> createUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsUserByEmail(createUserDTO.getEmail()) || userLoginRepository.existsUserLoginByUsername(createUserDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        User user = createUserEntityFromDTO(createUserDTO);
        UserLogin userLogin = createUserLoginEntityFromDTO(user, createUserDTO);
        userRepository.save(user);
        userLoginRepository.save(userLogin);
        user.setUserLogin(userLogin);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}