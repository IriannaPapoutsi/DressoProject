package gr.dresso.rest.services.impl;

import gr.dresso.rest.dto.CreateUserDTO;
import gr.dresso.rest.dto.UpdateUserDTO;
import gr.dresso.rest.entities.User;
import gr.dresso.rest.entities.UserLogin;
import gr.dresso.rest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getAllUsers_shouldReturnAllUsers() {
        // Given
        User user1 = new User();
        User user2 = new User();
        List<User> userList = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<User> actualUsers = userService.getAllUsers();

        // Then
        assertEquals("getAllUsers() should use repository to get users", userList, actualUsers);
    }

    @Test
    public void createUserEntityFromDTO_shouldReturnUserObject() {
        // Given
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("IT456")
                .country("Italy")
                .city("Turin, San Salvario")
                .address("Via Po 23")
                .email("marpap@gmail.com")
                .username("marpap")
                .password("CantWait!9")
                .build();

        User expectedUser = User.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("IT456")
                .country("Italy")
                .city("Turin, San Salvario")
                .address("Via Po 23")
                .email("marpap@gmail.com")
                .credits(15.0)
                .build();

        // When
        User actualUser = userService.createUserEntityFromDTO(createUserDTO);

        // Then
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    public void createUserLoginEntityFromDTO_shouldReturnUserLoginObject() {
        // Given
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("IT456")
                .country("Italy")
                .city("Turin, San Salvario")
                .address("Via Po 23")
                .email("marpap@gmail.com")
                .username("marpap")
                .password("CantWait!9")
                .build();

        User user = User.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("IT456")
                .country("Italy")
                .city("Turin, San Salvario")
                .address("Via Po 23")
                .email("marpap@gmail.com")
                .credits(15.0)
                .build();

        UserLogin expectedUserLogin = UserLogin.builder()
                .user(user)
                .username("marpap")
                .password("CantWait!9")
                .build();

        // When
        UserLogin actualUserLogin = userService.createUserLoginEntityFromDTO(user, createUserDTO);

        // Then
        assertThat(actualUserLogin).usingRecursiveComparison().isEqualTo(expectedUserLogin);
    }

    @Test
    public void updateUserEntityFromDTO_shouldReturnUpdateUserObject() {
        // Given
        int userId = 1;
        User existedUserInDB = User.builder()
                .firstName("Irianna")
                .lastName("Papoutsi")
                .postalCode("11526")
                .country("Greece")
                .city("Athens")
                .address("Rostoviou 30-32")
                .email("iriannapapoutsi@gmail.com")
                .credits(50.0)
                .build();
        UpdateUserDTO updateUserDTO = UpdateUserDTO.builder()
                .country("Italy")
                .city("Turin, Borgo San Paolo")
                .postalCode("IT345")
                .address("Via Nizza 34")
                .build();
        User expectedUpdatedUser = User.builder()
                .firstName("Irianna")
                .lastName("Papoutsi")
                .postalCode("IT345")
                .country("Italy")
                .city("Turin, Borgo San Paolo")
                .address("Via Nizza 34")
                .email("iriannapapoutsi@gmail.com")
                .credits(50.0)
                .build();
        when(userRepository.findById(1)).thenReturn(Optional.of(existedUserInDB));

        // When
        User actualUpdateUser = userService.updateUserEntityFromDTO(updateUserDTO, userId);

        // Then
        assertThat(actualUpdateUser).usingRecursiveComparison().isEqualTo(expectedUpdatedUser);
    }

    @Test
    public void updateUserLoginEntityFromDTO_shouldReturnUpdateUserLoginObject() {
        // Given
        User existedUserInDB = User.builder()
                .firstName("Irianna")
                .lastName("Papoutsi")
                .postalCode("11526")
                .country("Greece")
                .city("Athens")
                .address("Rostoviou 30-32")
                .email("iriannapapoutsi@gmail.com")
                .credits(50.0)
                .build();
        UserLogin existedUserLoginInDB = UserLogin.builder()
                .password("UpToNewThings!!3")
                .username("italian-iri")
                .build();
        existedUserInDB.setUserLogin(existedUserLoginInDB);
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setPassword("NewerThingsOnTheWay^2");
        UserLogin expectedUserLoginInDB = existedUserLoginInDB;
        expectedUserLoginInDB.setPassword("NewerThingsOnTheWay^2");

        // When
        UserLogin actualUpdateUserLogin = userService.updateUserLoginEntityFromDTO(existedUserInDB, updateUserDTO);

        // Then
        assertThat(actualUpdateUserLogin).usingRecursiveComparison().isEqualTo(expectedUserLoginInDB);
    }


}
