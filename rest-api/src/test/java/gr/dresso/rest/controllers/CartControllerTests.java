package gr.dresso.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.dresso.rest.dto.CartDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createCart_givenValidRequestBody_shouldReturnCreatedStatus() throws Exception {
        // Given
        CartDTO cartDTO = new CartDTO("1", "1");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(cartDTO)))
                // Then
                .andExpect(status().isCreated());
    }

    @Test
    public void createCart_givenInvalidUserIdRequestBody_shouldReturnBadRequestStatus() throws Exception {
        // Given
        CartDTO cartDTO = new CartDTO("10000", "1");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(cartDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCart_givenInvalidProductIdRequestBody_shouldReturnBadRequestStatus() throws Exception {
        // Given
        CartDTO cartDTO = new CartDTO("1", "10000");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(cartDTO)))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCart_givenValidUserIdRequestParam_shouldReturn() throws Exception{
        // Given
        String userId = "2";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/cart?userId=" + userId))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCart_givenInvalidUserIdRequestParam_shouldReturnNotFoundStatus() throws Exception {
        // Given
        String userId = "10000";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/cart?userId=" + userId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCartItem_givenValidCartDTORequestBody_shouldReturnOkStatus() throws Exception {
        // Given
        CartDTO cartDTO = new CartDTO("2", "3");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/cart/item")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(cartDTO)))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCartItem_givenInvalidCartDTOProductIdRequestBody_shouldReturnNotFoundStatus() throws Exception {
        // Given
        CartDTO cartDTO = new CartDTO("2", "30");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/cart/item")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(cartDTO)))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCartItem_givenInvalidCartDTOUserIdRequestBody_shouldReturnNotFoundStatus() throws Exception {
        // Given
        CartDTO cartDTO = new CartDTO("20", "3");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/cart/item")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(cartDTO)))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkoutBalance_userIdDoesNotExists_shouldReturnNotFoundStatus() throws Exception {
        // Given
        String userId = "10000";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/cart/checkout?userId=" + userId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkoutBalance_userIdExistsAndHasEnoughBalance_shouldReturnOkStatus() throws Exception {
        // Given
        String userId = "2";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/cart/checkout?userId=" + userId))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void checkoutBalance_userIdExistsButDoesNotHaveEnoughBalance_shouldReturnConflictStatus() throws Exception {
        // Given
        String userId = "3";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/cart/checkout?userId=" + userId))
                // Then
                .andExpect(status().isConflict());
    }

    @Test
    public void getCartProductsByUserId_userIdDoesNotExist_shouldReturnNotFoundStatus() throws Exception {
        // Given
        String userId = "10000";

        // When
        mockMvc.perform(get("/api/cart?userId=" + userId))

                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCartProductsByUserId_userIdExists_shouldReturnCartOfUser() throws Exception {
        // Given
        String userId = "2";

        // When
        mockMvc.perform(get("/api/cart?userId=" + userId))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is("3")))
                .andExpect(jsonPath("$.[0].name", is("Lacoste Polo T-shirt")))
                .andExpect(jsonPath("$.[0].price", is(34.99)))
                .andExpect(jsonPath("$.[1].id", is("10")))
                .andExpect(jsonPath("$.[1].name", is("Slim Fit Mesh Polo Shirt")))
                .andExpect(jsonPath("$.[1].price", is(99.0)));
    }

}
