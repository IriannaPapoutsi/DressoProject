package gr.dresso.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCartProductsByUserId_userIdDoesNotExist_shouldReturnEmptyCart() throws Exception {
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
