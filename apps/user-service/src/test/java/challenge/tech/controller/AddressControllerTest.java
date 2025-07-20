package challenge.tech.controller;

import challenge.tech.domain.Address;
import challenge.tech.dto.paremeter.AddressParameter;
import challenge.tech.usecase.address.CreateAddressUseCase;
import challenge.tech.usecase.address.DeleteAddressUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private CreateAddressUseCase createAddressUseCase;
    @Mock
    private DeleteAddressUseCase deleteAddressUseCase;

    @InjectMocks
    private AddressController addressController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createAddress_shouldReturnCreatedAddress() throws Exception {
        AddressParameter parameter = new AddressParameter();
        parameter.setStreet("Test Street");
        parameter.setNumber("123");
        parameter.setCity("Test City");
        parameter.setState("TS");
        parameter.setZipCode("12345-678");

        Address createdAddress = new Address();
        createdAddress.setId(1L);
        createdAddress.setStreet("Test Street");

        when(createAddressUseCase.execute(any(Address.class))).thenReturn(createdAddress);

        mockMvc.perform(post("/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.street").value("Test Street"));
    }

    @Test
    void deleteAddress_shouldReturnNoContent() throws Exception {
        doNothing().when(deleteAddressUseCase).execute(anyLong());

        mockMvc.perform(delete("/v1/addresses/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
