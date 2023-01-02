package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.CreateUserDTO;
import gr.dresso.rest.dto.UserLoginDTO;
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

    private static final double DEFAULT_CREDITS_ON_SIGN_UP = 15.0;

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private User createUserEntityFromDTO(CreateUserDTO createUserDTO) {
        return User.builder()
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .postalCode(createUserDTO.getPostalCode())
                .country(createUserDTO.getCountry())
                .city(createUserDTO.getCity())
                .address(createUserDTO.getAddress())
                .email(createUserDTO.getEmail())
                .credits(DEFAULT_CREDITS_ON_SIGN_UP)
                .build();
    }

    private UserLogin createUserLoginEntityFromDTO(User user, CreateUserDTO createUserDTO) {
        return UserLogin.builder()
                .user(user)
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .build();
    }

    @Override
    public ResponseEntity<User> createUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsUserByEmail(createUserDTO.getEmail()) ||
                userLoginRepository.existsUserLoginByUsername(createUserDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = createUserEntityFromDTO(createUserDTO);
        UserLogin userLogin = createUserLoginEntityFromDTO(user, createUserDTO);
        user.setUserLogin(userLogin);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Override
    public ResponseEntity<Void> checkUserLogin(UserLoginDTO userLoginDTO) {
        if (userLoginRepository.existsUserLoginByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
