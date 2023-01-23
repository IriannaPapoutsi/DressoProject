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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTests {

    private static final String STATIC_URI = "/api/products";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllProducts_shouldReturnCorrectListSize() throws Exception {
        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(17)))
                .andExpect(jsonPath("$.[2].name", Matchers.is("Lacoste Polo T-shirt")))
                .andExpect(jsonPath("$.[2].price", Matchers.is(34.99)))
                .andExpect(jsonPath("$.[2].description", Matchers.is("Polo T-shirt in green color. " +
                        "An all time classic item which has been loved by young and old alike.")))
                .andExpect(jsonPath("$.[2].sku", Matchers.is("PTS234")))
                .andExpect(jsonPath("$.[2].stock", Matchers.is(890)))
                .andExpect(jsonPath("$.[2].color.id", Matchers.is(3)))
                .andExpect(jsonPath("$.[2].color.name", Matchers.is("Green")))
                .andExpect(jsonPath("$.[2].category.id", Matchers.is(3)))
                .andExpect(jsonPath("$.[2].category.name", Matchers.is("T-shirt")))
                .andExpect(jsonPath("$.[2].images[0].id", Matchers.is(3)))
                .andExpect(jsonPath("$.[2].images[0].imageTitle", Matchers.is("Polo T-shirt")))
                .andExpect(jsonPath("$.[2].images[0].imageURL",
                        Matchers.is("https://shoemania21.com/wp-content/uploads/2022/12/sc_007544_a.jpeg")));
    }

    @Test
    public void getAllProducts_providedValidName_shouldReturnExpectedProducts() throws Exception {
        // Given
        String name = "Polo T-shirt";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("name", name))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Lacoste Polo T-shirt")))
                .andExpect(jsonPath("$.[0].price", Matchers.is(34.99)))
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
                        Matchers.is("https://shoemania21.com/wp-content/uploads/2022/12/sc_007544_a.jpeg")));
    }

    @Test
    public void getAllProducts_givenInvalidNameRequestParam_shouldReturnZeroListSize() throws Exception {
        // Given
        String name = "Something else";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("name", name))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllProducts_givenValidCategoryRequestParam_shouldReturnOkStatus() throws Exception {
        // Given
        String categoryName = "Co";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("category", categoryName))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Wool-Blend Dad Coat")))
                .andExpect(jsonPath("$.[0].price", Matchers.is(240.0)))
                .andExpect(jsonPath("$.[0].description", Matchers.is("Our classic wool-blend dad coat in a " +
                        "tailored fit with luxe interior lining, side pockets and center button closure.")))
                .andExpect(jsonPath("$.[0].sku", Matchers.is("RC234")))
                .andExpect(jsonPath("$.[0].stock", Matchers.is(780)))
                .andExpect(jsonPath("$.[0].color.id", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].color.name", Matchers.is("Red")))
                .andExpect(jsonPath("$.[0].category.id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].category.name", Matchers.is("Coat")))
                .andExpect(jsonPath("$.[0].images[0].id", Matchers.is(7)))
                .andExpect(jsonPath("$.[0].images[0].imageTitle", Matchers.is("Red Coat")))
                .andExpect(jsonPath("$.[0].images[0].imageURL",
                        Matchers.is("https://img.abercrombie.com/is/image/anf/KIC_144-1509-1016-501_model4?policy=product-large")));
    }

    @Test
    public void getAllProducts_givenInvalidCategoryRequestParam_shouldReturnZeroListSize() throws Exception {
        // Given
        String categoryName = "Not a category";

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("category", categoryName))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllProducts_givenValidNameAndCategoryRequestParams_shouldReturnOkStatus() throws Exception {
        // Given
        String name = "Elegant Dress";
        String categoryName = "Dre";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("name", Collections.singletonList(name));
        queryParams.put("category", Collections.singletonList(categoryName));

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParams(queryParams))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Elegant Dress")))
                .andExpect(jsonPath("$.[0].id", Matchers.is(4)))
                .andExpect(jsonPath("$.[0].price", Matchers.is(154.99)))
                .andExpect(jsonPath("$.[0].description", Matchers.is("Mermaid style one-shoulder dress" +
                        " crafted from crystal fabric with a front side split design.")))
                .andExpect(jsonPath("$.[0].sku", Matchers.is("PD234")))
                .andExpect(jsonPath("$.[0].stock", Matchers.is(457)))
                .andExpect(jsonPath("$.[0].color.id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].color.name", Matchers.is("Purple")))
                .andExpect(jsonPath("$.[0].category.id", Matchers.is(4)))
                .andExpect(jsonPath("$.[0].category.name", Matchers.is("Dress")))
                .andExpect(jsonPath("$.[0].images[0].id", Matchers.is(4)))
                .andExpect(jsonPath("$.[0].images[0].imageTitle", Matchers.is("Maxi Dress")))
                .andExpect(jsonPath("$.[0].images[0].imageURL",
                        Matchers.is("https://www.nashbyna.com/wp-content/uploads/2022/01/nash-02-scarlet-purple-skouro-" +
                                "mov-forema-winter-summer-collection-nashbyna-natashaavloniti-greekdesigner-weddingdresses-episima-" +
                                "foremata-vradina-foremata-1.jpg")));
    }


    @Test
    public void getAllProducts_givenInvalidNameAndCategoryRequestParams_shouldReturnEmptyListOfProducts() throws Exception {
        // Given
        String name = "Name that does not exist";
        String categoryName = "Category that does not exist";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("name", Collections.singletonList(name));
        queryParams.put("category", Collections.singletonList(categoryName));

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get(STATIC_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParams(queryParams))
        // Then
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

}
