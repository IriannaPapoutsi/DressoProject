package gr.dresso.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.dresso.rest.dto.FavoriteProductDTO;
import gr.dresso.rest.entities.FavoriteProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FavoriteProductControllerTests {

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
    public void getAllFavoriteProductsByUserId_givenValidUserIdRequestParam_shouldReturnCorrectListSize() throws Exception {
        // Given
        String userID ="1";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/favorite-product?userId=" + userID)
                            .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getAllFavoriteProductsByUserId_givenInvalidUserIdRequestParam_shouldReturnNotFoundStatus() throws Exception {
        // Given
        String userID ="100000";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/favorite-product?userId=" + userID))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void createFavoriteProduct_givenValidRequestBody_shouldReturnCreatedStatus() throws Exception {
        // Given
        FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO("1", "8");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/favorite-product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(favoriteProductDTO)))
                // Then
                .andExpect(status().isCreated());

    }

    @Test
    public void createFavoriteProduct_givenInvalidUserIdRequestBody_shouldReturnNotFoundStatus() throws Exception {
        // Given
        FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO("100000", "8");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/favorite-product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(favoriteProductDTO)))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void createFavoriteProduct_givenInvalidProductIdRequestBody_shouldReturnNotFoundStatus() throws Exception {
        // Given
        FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO("1", "800000");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/favorite-product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(favoriteProductDTO)))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFavoriteProduct_givenValidRequestBody_shouldReturnOkStatus() throws Exception {
        // Given
        FavoriteProductDTO favoriteProductDTO =new FavoriteProductDTO("1", "14");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/favorite-product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(favoriteProductDTO)))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFavoriteProduct_givenInvalidUserIdRequestBody_shouldReturnNotFoundStatus() throws Exception {
        // Given
        FavoriteProductDTO favoriteProductDTO =new FavoriteProductDTO("100000", "14");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/favorite-product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(favoriteProductDTO)))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFavoriteProduct_givenInvalidProductIdRequestBody_shouldReturnNotFoundStatus() throws Exception {
        // Given
        FavoriteProductDTO favoriteProductDTO =new FavoriteProductDTO("1", "140000");

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/favorite-product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(favoriteProductDTO)))
                // Then
                .andExpect(status().isNotFound());
    }
}
