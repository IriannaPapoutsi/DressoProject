package gr.dresso.rest.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllProducts_shouldReturnCorrectListSize() throws Exception {
        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products")
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(17)));
    }

    @Test
    public void getProductsByName_givenValidRequestParam_shouldReturnOkStatus() throws Exception {
        // Given
        String name = "Mini Skirt";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products?name=" + name)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Mini Skirt")));
    }

    @Test
    public void getProductsByName_givenInvalidNameRequestParam_shouldReturnZeroListSize() throws Exception {
        // Given
        String name = "Mini ";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products?name=" + name)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    public void getProductsByCategory_givenValidRequestParam_shouldReturnOkStatus() throws Exception {
        // Given
        String categoryName = "Coat";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products?category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].category.name", Matchers.is("Coat")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    public void getProductsByCategory_givenInvalidCategoryRequestParam_shouldReturnZeroListSize() throws Exception {
        // Given
        String categoryName = "Co"; // No such category name exists

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products?category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    public void getProductsByNameAndCategoryName_givenValidRequestParams_shouldReturnOkStatus() throws Exception {
        // Given
        String name = "Elegant Dress";
        String categoryName = "Dress";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products?name=" + name + "&category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Elegant Dress")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].category.name", Matchers.is("Dress")));
    }

    @Test
    public void getProductsByNameAndCategoryName_givenInvalidRequestParams_shouldReturnZeroListSize() throws Exception {
        // Given
        String name = "Name that does not exist";
        String categoryName = "Category that does not exist";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products?name=" + name + "&category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }
}
