package gr.dresso.rest.controllers;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void getUserById_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 10000;

        // When
        mockMvc.perform(
                        get("/api/users/" + userId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserById_givenValidUserIdPathVar_shouldReturnOkStatus() throws Exception {
        // Given
        int userId = 1;

        // When
        mockMvc.perform(
                        get("/api/users/" + userId))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Irianna")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Papoutsi")));
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
                .andExpect(jsonPath("$.email", Matchers.is("mariannapapoutsi@gmail.com")));
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
    public void createUser_givenInvalidPasswordRequestBody_shouldReturnBadRequestStatus() throws Exception {
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
    public void deleteUserById_givenValidPathVar_shouldReturnOkStatus() throws Exception {
        // Given
        int userId = 5;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserById_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 100000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId))
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
        int userId = 1;

        // When
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users/" + userId)
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
        int userId = 100000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users/" + userId)
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
        int userId = 1;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users/" + userId)
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
        int userId = 1;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(updateUserDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllFavoriteProductsByUserId_givenValidUserIdPathVar_shouldReturnCorrectListSize() throws Exception {
        // Given
        int userId = 1;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/" + userId + "/favorite-products")
                            .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getAllFavoriteProductsByUserId_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 100000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/" + userId + "/favorite-products"))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void createFavoriteProduct_givenValidPathVars_shouldReturnCreatedStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isCreated());
    }

    @Test
    public void createFavoriteProduct_givenInvalidUserIdPathVar_shouldReturnConflictStatus() throws Exception {
        // Given
        int userId = 10000;
        int productId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isConflict());
    }

    @Test
    public void createFavoriteProduct_givenInvalidProductIdPathVar_shouldReturnConflictStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 3000000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isConflict());
    }

    @Test
    public void createFavoriteProduct_givenAlreadyExistedFavProductsVars_shouldReturnConflictStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 14;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteFavoriteProduct_givenValidPathVars_shouldReturnOkStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 14;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFavoriteProduct_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 100000;
        int productId = 14;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFavoriteProduct_givenInvalidProductIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 14000000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void addProductToCart_givenValidPathVars_shouldReturnCreatedStatus() throws Exception {
        // Given
        int userId = 5;
        int productId = 8;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isCreated());
    }

    @Test
    public void addProductToCart_givenInvalidUserIdPathVar_shouldReturnBadRequestStatus() throws Exception {
        // Given
        int userId = 500000;
        int productId = 8;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addProductToCart_givenInvalidProductIdPathVar_shouldReturnBadRequestStatus() throws Exception {
        // Given
        int userId = 5;
        int productId = 800000000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isBadRequest());
    }

      @Test
    public void deleteWholeUserCart_givenValidUserIdPathVar_shouldReturn() throws Exception{
        // Given
        int userId = 2;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId + "/cart-products/"))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteWholeUserCart_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 100000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId + "/cart-products/"))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteProductFromCart_givenValidPathVar_shouldReturnOkStatus() throws Exception {
        // Given
        int userId = 2;
        int productId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductFromCart_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 20000;
        int productId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteProductFromCart_givenInvalidProductIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 2;
        int productId = 30000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getProductsFromCartByUserId_givenValidUserId_shouldReturnCartOfUser() throws Exception {
        // Given
        int userId = 2;

        // When
        mockMvc.perform(get("/api/users/" + userId + "/cart-products")
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(3)))
                .andExpect(jsonPath("$.[0].name", is("Lacoste Polo T-shirt")))
                .andExpect(jsonPath("$.[0].price", is(34.99)))
                .andExpect(jsonPath("$.[1].id", is(10)))
                .andExpect(jsonPath("$.[1].name", is("Slim Fit Mesh Polo Shirt")))
                .andExpect(jsonPath("$.[1].price", is(99.0)));
    }

    @Test
    public void getProductsFromCartByUserId_givenInvalidUserId_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 20000;

        // When
        mockMvc.perform(get("/api/users/" + userId + "/cart-products"))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkoutBalance_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 10000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/checkout"))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkoutBalance_givenValiUserIdAndHasEnoughBalance_shouldReturnOkStatus() throws Exception {
        // Given
        int userId = 2;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/checkout"))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void checkoutBalance_givenValidUserIdButDoesNotHaveEnoughBalance_shouldReturnConflictStatus() throws Exception {
        // Given
        int userId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users/" + userId + "/checkout"))
                // Then
                .andExpect(status().isConflict());
    }
}
