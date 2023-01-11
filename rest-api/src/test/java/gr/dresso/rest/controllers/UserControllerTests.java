package gr.dresso.rest.controllers;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.dresso.rest.dto.CreateUserDTO;
import gr.dresso.rest.dto.UpdateUserDTO;
import gr.dresso.rest.dto.UserLoginDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllUsers_shouldReturnCorrectUserListSize() throws Exception {
        // When
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)));
    }

    @Test
    public void getUserById_givenInvalidUserIdRequestParam_shouldReturnNotFoundStatus() throws Exception {
        // Given
        String userId = "10000";

        // When
        mockMvc.perform(
                        get("/api/users/user?userId=" + userId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserById_givenValidUserIdRequestParam_shouldReturnOkStatus() throws Exception {
        // Given
        String userId = "1";

        // When
        mockMvc.perform(
                        get("/api/users/user?userId=" + userId))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Irianna")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Papoutsi")));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createUser_givenValidRequestBody_shouldReturnCreatedStatus() throws Exception {
        // Given
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("17526")
                .country("Italy")
                .city("Turin")
                .address("Via Verde 28")
                .email("mariannapapoutsi@gmail.com")
                .username("MariannaP")
                .password("OneMonthToGo9!")
                .build();

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(createUserDTO)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("mariannapapoutsi@gmail.com")));

    }

    @Test
    public void createUser_givenInvalidRequestBody_shouldReturnBadRequestStatus() throws Exception {
        // Given
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("17526")
                // Missing Country argument
                .city("Turin")
                .address("Via Verde 28")
                .email("mariannapapoutsi@gmail.com")
                .username("MariannaP")
                .password("OneMonthToGo9!")
                .build();

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(createUserDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUser_givenInvalidPasswordArgumentRequestBody_shouldReturnBadRequestStatus() throws Exception {
        // Given
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("17526")
                .country("Italy")
                .city("Turin")
                .address("Via Verde 28")
                .email("mariannapapoutsi@gmail.com")
                .username("MariannaP")
                // Numbers are required in password
                .password("OneMonthToGo!")
                .build();

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(createUserDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUser_givenAlreadyExistedUsernameRequestBody_shouldReturnBadRequestStatus() throws Exception {
        // Given
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("17526")
                .country("Italy")
                .city("Turin")
                .address("Via Verde 28")
                .email("mariannapapoutsi@gmail.com")
                // Below username already exists in database
                .username("iriannapap")
                .password("OneMonthToGo9!")
                .build();

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(createUserDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void login_givenValidRequestBody_shouldReturnOkStatus() throws Exception {
        // Given
        UserLoginDTO userLoginDTO = new UserLoginDTO("iriannapap", "CodeHubrocks1!");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userLoginDTO)))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void login_givenInvalidPasswordRequestBody_shouldReturnUnauthorizedStatus() throws Exception {
        // Given
        UserLoginDTO userLoginDTO = new UserLoginDTO("iriannapap", "CodeHubrocks23!"); // Wrong password given

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userLoginDTO)))
                // Then
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteUserById_givenValidRequestParam_shouldReturnOkStatus() throws Exception {
        // Given
        String userId = "5";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users?userId=" + userId))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserById_givenInvalidUserIdRequestParam_shouldReturnNotFoundStatus() throws Exception {
        // Given
        String userId = "100000";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users?userId=" + userId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserProfile_givenValidRequestBody_shouldReturnOkStatus() throws Exception {
        // Given
        UpdateUserDTO updateUserDTO = UpdateUserDTO
                .builder()
                .country("Italy")
                .city("Turin")
                .address("Via Verde 28")
                .postalCode("56734")
                .build();
        String userId = "1";

        // When
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users?userId=" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateUserDTO)))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserProfile_givenInvalidUserIdRequestBody_shouldReturnNotFoundStatus() throws Exception {
        // Given
        UpdateUserDTO updateUserDTO = UpdateUserDTO
                .builder()
                .country("Italy")
                .city("Turin")
                .address("Via Verde 28")
                .postalCode("56734")
                .build();
        String userId = "100000";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users?userId=" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(updateUserDTO)))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserProfile_givenInvalidPostalCodeRequestBody_shouldReturnBadRequestStatus() throws Exception {
        // Given
        UpdateUserDTO updateUserDTO = UpdateUserDTO
                .builder()
                .country("Italy")
                .city("Turin")
                .address("Via Verde 28")
                // Postal Code should be 5 numbers exactly
                .postalCode("56734$#$")
                .build();
        String userId = "1";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users?userId=" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(updateUserDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserProfile_givenInvalidPasswordRequestBody_shouldReturnBadRequestStatus() throws Exception {
        // Given
        UpdateUserDTO updateUserDTO = UpdateUserDTO
                .builder()
                .country("Italy")
                .city("Turin")
                .address("Via Verde 28")
                // Postal Code should be 5 numbers exactly
                .postalCode("56734$#$")
                .password("Missingnumbers!")
                .build();
        String userId = "1";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users?userId=" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(updateUserDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }
}
