package challenge.tech.controller;

import challenge.tech.domain.Product;
import challenge.tech.dto.paremeter.CreateProductParameter;
import challenge.tech.dto.paremeter.UpdateProductParameter;
import challenge.tech.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private FindAllUseCase findAllUseCase;
    @Mock
    private FindByIdUseCase findByIdUseCase;
    @Mock
    private FindBySkuUseCase findBySkuUseCase;
    @Mock
    private CreateUseCase createUseCase;
    @Mock
    private UpdateUseCase updateUseCase;
    @Mock
    private DeleteUseCase deleteUseCase;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new org.springframework.data.web.PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAllUsers_shouldReturnPageOfProducts() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setSku("SKU123");
        product.setName("Product 1");
        product.setPrice(new BigDecimal("10.00"));
        List<Product> productList = Collections.singletonList(product);
        Page<Product> productPage = new PageImpl<>(productList, PageRequest.of(0, 10), 1);

        when(findAllUseCase.execute(PageRequest.of(0, 10))).thenReturn(productPage);

        mockMvc.perform(get("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Product 1"));
    }

    @Test
    void findById_shouldReturnProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setSku("SKU123");
        product.setName("Product 1");
        product.setPrice(new BigDecimal("10.00"));

        when(findByIdUseCase.execute(anyLong())).thenReturn(product);

        mockMvc.perform(get("/v1/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"));
    }

    @Test
    void findBySku_shouldReturnProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setSku("SKU123");
        product.setName("Product 1");
        product.setPrice(new BigDecimal("10.00"));

        when(findBySkuUseCase.execute(anyString())).thenReturn(product);

        mockMvc.perform(get("/v1/products/sku/{sku}", "SKU123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }

    @Test
    void create_shouldReturnCreatedProduct() throws Exception {
        CreateProductParameter parameter = new CreateProductParameter("New Product", "SKU123", new BigDecimal("20.00"));
        Product createdProduct = new Product();
        createdProduct.setId(1L);
        createdProduct.setSku("SKU123");
        createdProduct.setName("New Product");
        createdProduct.setPrice(new BigDecimal("20.00"));

        when(createUseCase.execute(any(Product.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Product"));
    }

    @Test
    void update_shouldReturnUpdatedProduct() throws Exception {
        UpdateProductParameter parameter = new UpdateProductParameter("Updated Product", "SKU123", new BigDecimal("30.00"));
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setSku("SKU123");
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(new BigDecimal("30.00"));

        when(updateUseCase.execute(anyLong(), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/v1/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        doNothing().when(deleteUseCase).execute(anyLong());

        mockMvc.perform(delete("/v1/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
