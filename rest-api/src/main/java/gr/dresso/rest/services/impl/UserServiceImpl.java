package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.CreateUserDTO;
import gr.dresso.rest.dto.UpdateUserDTO;
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
import java.util.Optional;

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

    @Override
    public ResponseEntity<User> getUser(int userId) {
        // TODO: Here you do not need to call existsById()
        // TODO: Below, you are using findById which returns an Optional (meaning that it can be empty if the user does not exist)
        // TODO: You can get the optional first and call if (!userResponse.isPresent()) to see if the user exists
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<User> userResponse = userRepository.findById(userId);
        User user = userResponse.get();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    User createUserEntityFromDTO(CreateUserDTO createUserDTO) {
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

    UserLogin createUserLoginEntityFromDTO(User user, CreateUserDTO createUserDTO) {
        return UserLogin.builder()
                .user(user)
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .build();
    }

    @Override
    public ResponseEntity<User> createUser(CreateUserDTO createUserDTO) {
        // TODO: Maybe the if statement below should return 409 Conflict?
        // TODO: Generally it's a good idea to store validations into boolean variables with a name so that your goal is clearer to someone else who is reading the code
        // TODO: e.g. boolean shouldNotCreateAccount = userRepository.existsUserByEmail(createUserDTO.getEmail()) || userLoginRepository.existsUserLoginByUsername(createUserDTO.getUsername())
        // TODO: Then you can use the above in the if statement
        if (userRepository.existsUserByEmail(createUserDTO.getEmail()) ||
                userLoginRepository.existsUserLoginByUsername(createUserDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        User user = createUserEntityFromDTO(createUserDTO);
        UserLogin userLogin = createUserLoginEntityFromDTO(user, createUserDTO);
        user.setUserLogin(userLogin);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Override
    public ResponseEntity<Void> checkUserLogin(UserLoginDTO userLoginDTO) {
        if (userLoginRepository.existsUserLoginByUsernameAndPassword(userLoginDTO.getUsername(),
                userLoginDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    public ResponseEntity<String> deleteUserById(int userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // TODO: Here you do not need the userResponse variable, you do not use it anywhere
        Optional<User> userResponse = userRepository.findById(userId);
        User user = userResponse.get();
        userRepository.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted!");
    }

    User updateUserEntityFromDTO(UpdateUserDTO updateUserDTO, int userId) {
        Optional<User> userResponse = userRepository.findById(userId);
        User user = userResponse.get();
        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
        }
        if (updateUserDTO.getPostalCode() != null) {
            user.setPostalCode(updateUserDTO.getPostalCode());
        }
        if (updateUserDTO.getCountry() != null) {
            user.setCountry(updateUserDTO.getCountry());
        }
        if (updateUserDTO.getCity() != null) {
            user.setCity(updateUserDTO.getCity());
        }
        if (updateUserDTO.getAddress() != null) {
            user.setAddress(updateUserDTO.getAddress());
        }
        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }
        return user;
    }

    UserLogin updateUserLoginEntityFromDTO(User user, UpdateUserDTO updateUserDTO) {
        UserLogin userLogin = user.getUserLogin();
        if (updateUserDTO.getUsername() != null) {
            userLogin.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getPassword() != null) {
            userLogin.setPassword(updateUserDTO.getPassword());
        }
        return userLogin;
    }

    @Override
    public ResponseEntity<User> updateUser(UpdateUserDTO updateUserDTO, int userId) {
        // TODO: As I've mentioned on another TODO, you do not need to use the repository here,
        //  you can use the optional.isPresent() method by getting the user optional here instead of inside updateUserEntityFromDTO()
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = updateUserEntityFromDTO(updateUserDTO, userId);
        UserLogin userLogin = updateUserLoginEntityFromDTO(user, updateUserDTO);
        user.setUserLogin(userLogin);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
