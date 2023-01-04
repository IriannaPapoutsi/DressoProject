package gr.dresso.rest.services.impl;

import gr.dresso.rest.entities.User;
import gr.dresso.rest.repositories.UserLoginRepository;
import gr.dresso.rest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserLoginRepository userLoginRepository;

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

}
