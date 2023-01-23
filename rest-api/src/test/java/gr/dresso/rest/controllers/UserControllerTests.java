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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final String STATIC_URI = "/api/users";

    @Test
    public void getAllUsers_shouldReturnCorrectUserListSize() throws Exception {
        // When
        mockMvc.perform(get(STATIC_URI)
                        .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Irianna")))
                .andExpect(jsonPath("$[0].lastName", Matchers.is("Papoutsi")))
                .andExpect(jsonPath("$[0].postalCode", Matchers.is("11526")))
                .andExpect(jsonPath("$[0].country", Matchers.is("Greece")))
                .andExpect(jsonPath("$[0].city", Matchers.is("Athens")))
                .andExpect(jsonPath("$[0].address", Matchers.is("Rostoviou 30-32")))
                .andExpect(jsonPath("$[0].email", is("iriannapapoutsi@gmail.com")))
                .andExpect(jsonPath("$[0].credits", Matchers.is(50.0)))
                .andExpect(jsonPath("$[0].userLogin.username", Matchers.is("iriannapap")))
                .andExpect(jsonPath("$.userLogin.password").doesNotExist())
                .andExpect(jsonPath("$[1].email", is("glagoudakis@gmail.com")))
                .andExpect(jsonPath("$[2].email", is("gpapoutsis@gmail.com")))
                .andExpect(jsonPath("$[3].email", is("evoutierou@gmail.com")))
                .andExpect(jsonPath("$[4].email", is("epapoutsi@gmail.com")));
    }

    @Test
    public void getUserById_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 10000;

        // When
        mockMvc.perform(
                        get(STATIC_URI + userId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserById_givenValidUserIdPathVar_shouldReturnOkStatus() throws Exception {
        // Given
        int userId = 1;

        // When
        mockMvc.perform(
                        get(STATIC_URI + "/" + userId))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Irianna")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Papoutsi")))
                .andExpect(jsonPath("$.postalCode", Matchers.is("11526")))
                .andExpect(jsonPath("$.country", Matchers.is("Greece")))
                .andExpect(jsonPath("$.city", Matchers.is("Athens")))
                .andExpect(jsonPath("$.address", Matchers.is("Rostoviou 30-32")))
                .andExpect(jsonPath("$.email", is("iriannapapoutsi@gmail.com")))
                .andExpect(jsonPath("$.credits", Matchers.is(50.0)))
                .andExpect(jsonPath("$.userLogin.username", Matchers.is("iriannapap")))
                .andExpect(jsonPath("$.userLogin.password").doesNotHaveJsonPath());
    }

    private String transformObjectToJson(final Object obj) {
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
                        MockMvcRequestBuilders.post(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(createUserDTO)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", Matchers.is("Marianna")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Papoutsi")))
                .andExpect(jsonPath("$.postalCode", Matchers.is("17526")))
                .andExpect(jsonPath("$.country", Matchers.is("Italy")))
                .andExpect(jsonPath("$.city", Matchers.is("Turin")))
                .andExpect(jsonPath("$.address", Matchers.is("Via Verde 28")))
                .andExpect(jsonPath("$.email", is("mariannapapoutsi@gmail.com")))
                .andExpect(jsonPath("$.userLogin.username", Matchers.is("MariannaP")))
                .andExpect(jsonPath("$.userLogin.password").doesNotHaveJsonPath());
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
                        MockMvcRequestBuilders.post(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(createUserDTO)))
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
                        MockMvcRequestBuilders.post(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(createUserDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUser_givenAlreadyExistedEmailRequestBody_shouldReturnConflictStatus() throws Exception {
        // Given
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("Marianna")
                .lastName("Papoutsi")
                .postalCode("17526")
                .country("Italy")
                .city("Turin")
                .address("Via Verde 28")
                // Below email already exists in database
                .email("iriannapapoutsi@gmail.com")
                .username("mariannapap")
                .password("OneMonthToGo9!")
                .build();

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(createUserDTO)))
                // Then
                .andExpect(status().isConflict());
    }

    @Test
    public void createUser_givenAlreadyExistedUsernameRequestBody_shouldReturnConflictStatus() throws Exception {
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
                        MockMvcRequestBuilders.post(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(createUserDTO)))
                // Then
                .andExpect(status().isConflict());
    }


    @Test
    public void login_givenValidRequestBody_shouldReturnOkStatus() throws Exception {
        // Given
        UserLoginDTO userLoginDTO = new UserLoginDTO("iriannapap", "CodeHubrocks1!");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(userLoginDTO)))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void login_givenInvalidPasswordRequestBody_shouldReturnUnauthorizedStatus() throws Exception {
        // Given
        UserLoginDTO userLoginDTO = new UserLoginDTO("iriannapap", "CodeHubrocks23!"); // Wrong password given

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(userLoginDTO)))
                // Then
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteUserById_givenValidPathVar_shouldReturnOkStatus() throws Exception {
        // Given
        int userId = 5;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserById_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 100000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId))
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
                MockMvcRequestBuilders.put(STATIC_URI + "/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transformObjectToJson(updateUserDTO)))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postalCode", Matchers.is("56734")))
                .andExpect(jsonPath("$.country", Matchers.is("Italy")))
                .andExpect(jsonPath("$.city", Matchers.is("Turin")))
                .andExpect(jsonPath("$.address", Matchers.is("Via Verde 28")));
    }

    @Test
    public void updateUserProfile_givenNullDTO_shouldReturnNotUpdatedUser() throws Exception {
        // Given
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        int userId = 1;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.put(STATIC_URI + "/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(updateUserDTO)))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Irianna")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Papoutsi")))
                .andExpect(jsonPath("$.postalCode", Matchers.is("11526")))
                .andExpect(jsonPath("$.country", Matchers.is("Greece")))
                .andExpect(jsonPath("$.city", Matchers.is("Athens")))
                .andExpect(jsonPath("$.address", Matchers.is("Rostoviou 30-32")))
                .andExpect(jsonPath("$.email", is("iriannapapoutsi@gmail.com")))
                .andExpect(jsonPath("$.credits", Matchers.is(50.0)))
                .andExpect(jsonPath("$.userLogin.username", Matchers.is("iriannapap")))
                .andExpect(jsonPath("$.userLogin.password").doesNotHaveJsonPath());

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
                        MockMvcRequestBuilders.put(STATIC_URI + "/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(updateUserDTO)))
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
                        MockMvcRequestBuilders.put(STATIC_URI + "/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(updateUserDTO)))
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
                        MockMvcRequestBuilders.put(STATIC_URI + "/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transformObjectToJson(updateUserDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllFavoriteProductsByUserId_givenValidUserIdPathVar_shouldReturnCorrectListSize() throws Exception {
        // Given
        int userId = 1;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI + "/" + userId + "/favorite-products")
                            .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(14)))
                .andExpect(jsonPath("$[1].id", is(9)))
                .andExpect(jsonPath("$[2].id", is(8)))
                .andExpect(jsonPath("$[3].id", is(5)));
    }

    @Test
    public void getAllFavoriteProductsByUserId_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 100000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI + "/" + userId + "/favorite-products"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createFavoriteProduct_givenValidPathVars_shouldReturnCreatedStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.firstName", Matchers.is("Irianna")))
                .andExpect(jsonPath("$.user.lastName", Matchers.is("Papoutsi")))
                .andExpect(jsonPath("$.user.postalCode", Matchers.is("11526")))
                .andExpect(jsonPath("$.user.country", Matchers.is("Greece")))
                .andExpect(jsonPath("$.user.city", Matchers.is("Athens")))
                .andExpect(jsonPath("$.user.address", Matchers.is("Rostoviou 30-32")))
                .andExpect(jsonPath("$.user.email", is("iriannapapoutsi@gmail.com")))
                .andExpect(jsonPath("$.user.credits", Matchers.is(50.0)))
                .andExpect(jsonPath("$.user.userLogin.username", Matchers.is("iriannapap")))
                .andExpect(jsonPath("$.user.userLogin.password").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.product.name", Matchers.is("Lacoste Polo T-shirt")))
                .andExpect(jsonPath("$.product.price", Matchers.is(34.99)))
                .andExpect(jsonPath("$.product.description", Matchers.is("Polo T-shirt in green color. " +
                        "An all time classic item which has been loved by young and old alike.")))
                .andExpect(jsonPath("$.product.sku", Matchers.is("PTS234")))
                .andExpect(jsonPath("$.product.stock", Matchers.is(890)))
                .andExpect(jsonPath("$.product.color.id", Matchers.is(3)))
                .andExpect(jsonPath("$.product.color.name", Matchers.is("Green")))
                .andExpect(jsonPath("$.product.category.id", Matchers.is(3)))
                .andExpect(jsonPath("$.product.category.name", Matchers.is("T-shirt")))
                .andExpect(jsonPath("$.product.images[0].id", Matchers.is(3)))
                .andExpect(jsonPath("$.product.images[0].imageTitle", Matchers.is("Polo T-shirt")))
                .andExpect(jsonPath("$.product.images[0].imageURL",
                        Matchers.is("https://shoemania21.com/wp-content/uploads/2022/12/sc_007544_a.jpeg")));
    }

    @Test
    public void createFavoriteProduct_givenInvalidUserIdPathVar_shouldReturnIsNotFoundStatus() throws Exception {
        // Given
        int userId = 10000;
        int productId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void createFavoriteProduct_givenInvalidProductIdPathVar_shouldReturnIsNotFoundStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 3000000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void createFavoriteProduct_givenAlreadyExistedFavProductsVars_shouldReturnConflictStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 14;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/favorite-products/" + productId))
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
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Favorite Product successfully deleted!")));
    }

    @Test
    public void deleteFavoriteProduct_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 100000;
        int productId = 14;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId + "/favorite-products/" + productId))
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
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId + "/favorite-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void addProductToCart_givenValidPathVars_shouldReturnCreatedStatus() throws Exception {
        // Given
        int userId = 1;
        int productId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.firstName", Matchers.is("Irianna")))
                .andExpect(jsonPath("$.user.lastName", Matchers.is("Papoutsi")))
                .andExpect(jsonPath("$.user.postalCode", Matchers.is("11526")))
                .andExpect(jsonPath("$.user.country", Matchers.is("Greece")))
                .andExpect(jsonPath("$.user.city", Matchers.is("Athens")))
                .andExpect(jsonPath("$.user.address", Matchers.is("Rostoviou 30-32")))
                .andExpect(jsonPath("$.user.email", is("iriannapapoutsi@gmail.com")))
                .andExpect(jsonPath("$.user.credits", Matchers.is(50.0)))
                .andExpect(jsonPath("$.user.userLogin.username", Matchers.is("iriannapap")))
                .andExpect(jsonPath("$.user.userLogin.password").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.product.name", Matchers.is("Lacoste Polo T-shirt")))
                .andExpect(jsonPath("$.product.price", Matchers.is(34.99)))
                .andExpect(jsonPath("$.product.description", Matchers.is("Polo T-shirt in green color. " +
                        "An all time classic item which has been loved by young and old alike.")))
                .andExpect(jsonPath("$.product.sku", Matchers.is("PTS234")))
                .andExpect(jsonPath("$.product.stock", Matchers.is(890)))
                .andExpect(jsonPath("$.product.color.id", Matchers.is(3)))
                .andExpect(jsonPath("$.product.color.name", Matchers.is("Green")))
                .andExpect(jsonPath("$.product.category.id", Matchers.is(3)))
                .andExpect(jsonPath("$.product.category.name", Matchers.is("T-shirt")))
                .andExpect(jsonPath("$.product.images[0].id", Matchers.is(3)))
                .andExpect(jsonPath("$.product.images[0].imageTitle", Matchers.is("Polo T-shirt")))
                .andExpect(jsonPath("$.product.images[0].imageURL",
                        Matchers.is("https://shoemania21.com/wp-content/uploads/2022/12/sc_007544_a.jpeg")));
    }

    @Test
    public void addProductToCart_givenInvalidUserIdPathVar_shouldReturnIsNotFoundStatus() throws Exception {
        // Given
        int userId = 500000;
        int productId = 8;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void addProductToCart_givenInvalidProductIdPathVar_shouldReturnIsNotFoundStatus() throws Exception {
        // Given
        int userId = 5;
        int productId = 800000000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWholeUserCart_givenValidUserIdPathVar_shouldReturnOkStatus() throws Exception{
        // Given
        int userId = 2;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId + "/cart-products"))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteWholeUserCart_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 100000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId + "/cart-products"))
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
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId + "/cart-products/" + productId))
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
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId + "/cart-products/" + productId))
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
                        MockMvcRequestBuilders.delete(STATIC_URI + "/" + userId + "/cart-products/" + productId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getProductsFromCartByUserId_givenValidUserId_shouldReturnCartOfUser() throws Exception {
        // Given
        int userId = 2;

        // When
        mockMvc.perform(get(STATIC_URI + "/" + userId + "/cart-products")
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(3)))
                .andExpect(jsonPath("$.[0].name", is("Lacoste Polo T-shirt")))
                .andExpect(jsonPath("$.[0].price", is(34.99)))
                .andExpect(jsonPath("$.[0].description", Matchers.is("Polo T-shirt in green color. " +
                        "An all time classic item which has been loved by young and old alike.")))
                .andExpect(jsonPath("$.[0].sku", Matchers.is("PTS234")))
                .andExpect(jsonPath("$.[0].stock", Matchers.is(890)))
                .andExpect(jsonPath("$.[0].color.id", Matchers.is(3)))
                .andExpect(jsonPath("$.[0].color.name", Matchers.is("Green")))
                .andExpect(jsonPath("$.[0].category.id", Matchers.is(3)))
                .andExpect(jsonPath("$.[0].category.name", Matchers.is("T-shirt")))
                .andExpect(jsonPath("$.[0].images[0].id", Matchers.is(3)))
                .andExpect(jsonPath("$.[0].images[0].imageTitle", Matchers.is("Polo T-shirt")))
                .andExpect(jsonPath("$.[0].images[0].imageURL",
                        Matchers.is("https://shoemania21.com/wp-content/uploads/2022/12/sc_007544_a.jpeg")))
                .andExpect(jsonPath("$.[1].id", is(10)))
                .andExpect(jsonPath("$.[1].name", is("Slim Fit Mesh Polo Shirt")))
                .andExpect(jsonPath("$.[1].price", is(99.0)))
                .andExpect(jsonPath("$.[1].description", Matchers.is("An American style standard since 1972, " +
                        "the Polo shirt has been imitated but never matched. Over the decades, Ralph Lauren has re-imagined " +
                        "his signature style in a wide array of colours and fits, yet all retain the quality and attention to detail " +
                        "of the iconic original.")))
                .andExpect(jsonPath("$.[1].sku", Matchers.is("YT234")))
                .andExpect(jsonPath("$.[1].stock", Matchers.is(264)))
                .andExpect(jsonPath("$.[1].color.id", Matchers.is(5)))
                .andExpect(jsonPath("$.[1].color.name", Matchers.is("Yellow")))
                .andExpect(jsonPath("$.[1].category.id", Matchers.is(3)))
                .andExpect(jsonPath("$.[1].category.name", Matchers.is("T-shirt")))
                .andExpect(jsonPath("$.[1].images[0].id", Matchers.is(10)))
                .andExpect(jsonPath("$.[1].images[0].imageTitle", Matchers.is("Polo T-shirt")))
                .andExpect(jsonPath("$.[1].images[0].imageURL",
                        Matchers.is("https://www.rlmedia.io/is/image/PoloGSI/s7-1416944_alternate10?$rl_df_zoom_a10$")));
    }

    @Test
    public void getProductsFromCartByUserId_givenInvalidUserId_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 20000;

        // When
        mockMvc.perform(get(STATIC_URI + "/" + userId + "/cart-products"))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkoutBalance_givenInvalidUserIdPathVar_shouldReturnNotFoundStatus() throws Exception {
        // Given
        int userId = 10000;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/checkout"))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkoutBalance_givenValidUserIdAndHasEnoughBalance_shouldReturnOkStatus() throws Exception {
        // Given
        int userId = 2;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/checkout"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("User credits left: 66.01")));
    }

    @Test
    public void checkoutBalance_givenValidUserIdButDoesNotHaveEnoughBalance_shouldReturnConflictStatus() throws Exception {
        // Given
        int userId = 3;

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post(STATIC_URI + "/" + userId + "/checkout"))
                // Then
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$", is("Total cost: 291.49 user credits: 20.0")));
    }

}
