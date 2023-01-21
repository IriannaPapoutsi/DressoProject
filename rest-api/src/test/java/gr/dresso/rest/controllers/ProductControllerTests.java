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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: Add @DirtiesContext to make Spring reset your database after this test class is executed.
// TODO: After each test class is executed, the database scripts will be run again and if the database is not reset with @DirtiesContext
// TODO: then it will create conflicts and the test will fail
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTests {

    // TODO: Good job on testing all scenarios :D !

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
                // TODO: I would also check the actual content of at least 1 product
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(17)));
    }

    // TODO: The first part of the methods names should be the name of the method that is being tested -> getAllProducts
    // TODO: I would rename this to something like getAllProducts_providedValidName_shouldReturnExpectedProducts
    @Test
    public void getProductsByName_givenValidRequestParam_shouldReturnOkStatus() throws Exception {
        // Given
        String name = "Mini Skirt";

        // When
        mockMvc.perform(
                // TODO: You can pass the name via the .queryParam() after .contentType() instead of the URL
                        MockMvcRequestBuilders.get("/api/products?name=" + name)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                // TODO: I would also check the rest of the field that I would want to appear on the product
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Mini Skirt")));
    }

    // TODO: The first part of the methods names should be the name of the method that is being tested -> getAllProducts
    @Test
    public void getProductsByName_givenInvalidNameRequestParam_shouldReturnZeroListSize() throws Exception {
        // Given
        String name = "Something else";

        // When
        mockMvc.perform(
                        // TODO: You can pass the name via the .queryParam() after .contentType() instead of the URL
                        MockMvcRequestBuilders.get("/api/products?name=" + name)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                // TODO: Validate the status code here
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    // TODO: The first part of the methods names should be the name of the method that is being tested -> getAllProducts
    // TODO: I would rename this to something like getAllProducts_providedValidCategory_shouldReturnExpectedProducts
    @Test
    public void getProductsByCategory_givenValidRequestParam_shouldReturnOkStatus() throws Exception {
        // Given
        String categoryName = "Coat";

        // When
        mockMvc.perform(
                        // TODO: You can pass the category via the .queryParam() after .contentType() instead of the URL
                        MockMvcRequestBuilders.get("/api/products?category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                // TODO: I would also validate the other values of the product
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].category.name", Matchers.is("Coat")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    // TODO: The first part of the methods names should be the name of the method that is being tested -> getAllProducts
    // TODO: I believe having this and the above test is a bit overkill (I would keep one of the two)
    @Test
    public void getProductsByCategory_givenValidStringsRequestParam_shouldReturnOkStatus() throws Exception {
        // Given
        String categoryName = "Co";

        // When
        mockMvc.perform(
                        // TODO: You can pass the category via the .queryParam() after .contentType() instead of the URL
                        MockMvcRequestBuilders.get("/api/products?category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].category.name", Matchers.is("Coat")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    // TODO: The first part of the methods names should be the name of the method that is being tested -> getAllProducts
    @Test
    public void getProductsByCategory_givenInvalidCategoryRequestParam_shouldReturnZeroListSize() throws Exception {
        // Given
        String categoryName = "Not a category";

        // When
        mockMvc.perform(
                        // TODO: You can pass the category via the .queryParam() after .contentType() instead of the URL
                        MockMvcRequestBuilders.get("/api/products?category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                // TODO: Validate the status code here
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    // TODO: The first part of the methods names should be the name of the method that is being tested -> getAllProducts
    // TODO: I would call this getAllProducts_givenValidNameAndCategory_shouldReturnExpectedProducts
    @Test
    public void getProductsByNameAndCategoryName_givenValidRequestParams_shouldReturnOkStatus() throws Exception {
        // Given
        String name = "Elegant Dress";
        String categoryName = "Dress";

        // When
        mockMvc.perform(
                        // TODO: You can pass the name and category via the .queryParam() after .contentType() instead of the URL
                        MockMvcRequestBuilders.get("/api/products?name=" + name + "&category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                // TODO: I would also check the other values of the products
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Elegant Dress")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].category.name", Matchers.is("Dress")));
    }

    // TODO: The first part of the methods names should be the name of the method that is being tested -> getAllProducts
    // TODO: I would call this something like getAllProducts_givenInvalidNameAndCategory_shouldReturnEmptyListOfProducts
    @Test
    public void getProductsByNameAndCategoryName_givenInvalidRequestParams_shouldReturnZeroListSize() throws Exception {
        // Given
        String name = "Name that does not exist";
        String categoryName = "Category that does not exist";

        // When
        mockMvc.perform(
                        // TODO: You can pass the name and category via the .queryParam() after .contentType() instead of the URL
                        MockMvcRequestBuilders.get("/api/products?name=" + name + "&category=" + categoryName)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }
}
