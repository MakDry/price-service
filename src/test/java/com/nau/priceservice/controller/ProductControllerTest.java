package com.nau.priceservice.controller;

import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.module.price.PriceCommand;
import com.nau.priceservice.module.price.PriceQuery;
import com.nau.priceservice.module.price.interfaces.PriceCommandHandler;
import com.nau.priceservice.module.price.interfaces.PriceQueryHandler;
import com.nau.priceservice.module.product.ProductCommand;
import com.nau.priceservice.module.product.ProductQuery;
import com.nau.priceservice.module.product.interfaces.ProductCommandHandler;
import com.nau.priceservice.module.product.interfaces.ProductQueryHandler;
import com.nau.priceservice.util.dto.PriceDto;
import com.nau.priceservice.util.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductQueryHandler<ProductDto, ProductQuery> productQueryHandler;
    @MockBean
    private PriceQueryHandler<PriceDto, PriceQuery> priceQueryHandler;
    @MockBean
    private ProductCommandHandler<ProductDto, ProductCommand> productCommandHandler;
    @MockBean
    private PriceCommandHandler<PriceDto, PriceCommand> priceCommandHandler;

    private PriceDto priceDto1;
    private PriceDto priceDto2;
    private ProductDto productDto1;
    private ProductDto productDto2;

    @BeforeEach
    void setUp() {
        priceDto1 = new PriceDto();
        priceDto1.setId("1");
        priceDto1.setProductId("101");
        priceDto1.setCurrency("euro");
        priceDto1.setUnitAmount(100);
        priceDto1.setUnitAmountDecimal(100);
        priceDto1.setPurchasePrice(80);
        priceDto1.setSuggestedAmount(90);
        priceDto2 = new PriceDto();
        priceDto2.setId("2");
        priceDto2.setProductId("102");
        priceDto2.setCurrency("euro");
        priceDto2.setUnitAmount(80);
        priceDto2.setUnitAmountDecimal(80);
        priceDto2.setPurchasePrice(70);
        priceDto2.setSuggestedAmount(75);
        productDto1 = new ProductDto();
        productDto1.setId("1");
        productDto1.setExternalId("101");
        productDto1.setTitle("Apples");
        productDto2 = new ProductDto();
        productDto2.setId("2");
        productDto2.setExternalId("102");
        productDto2.setTitle("Pineapples");
    }

    @Test
    void ProductController_getAllProducts_ReturnsListOfProducts() throws Exception {
        when(productQueryHandler.getAll()).thenReturn(List.of(productDto1, productDto2));

        mvc.perform(get("/products")).andExpectAll(
                status().isOk(),
                jsonPath("$.length()").value(2));
    }

    @Test
    void ProductController_isProductExists_ReturnsTrueIfProductExists() throws Exception {
        when(productQueryHandler.isExists(Mockito.anyString())).thenReturn(true);

        MvcResult result = mvc.perform(get("/products").param("p", "1"))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();

        Assertions.assertThat(resultContent).isEqualTo("true");
    }

    @Test
    void ProductController_isProductExists_ReturnsFalseIfProductDoesntExists() throws Exception {
        when(productQueryHandler.isExists(Mockito.anyString())).thenReturn(false);

        String resultContent = mvc.perform(get("/products").param("p", "1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(resultContent).isEqualTo("false");
    }

    @Test
    void ProductController_createProduct_ReturnsSavedProductWithAllFields() throws InvalidProductException, Exception {
        String jsonRequest = "{\"externalId\":\"101\",\"title\":\"Apples\"}";

        when(productCommandHandler.handleCreate(Mockito.any(ProductCommand.class))).thenReturn(productDto1);

        mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").value("1"),
                        jsonPath("$.externalId").value("101"),
                        jsonPath("$.title").value("Apples"));
    }

    @Test
    void ProductController_updateProduct_ReturnsUpdatedProductWithAllFields() throws Exception, InvalidDtoException {
        productDto1.setTitle("Cucumber");
        String jsonRequest = "{\"title\":\"Cucumber\"}";

        when(productCommandHandler.handleUpdate(Mockito.anyString(), Mockito.any(ProductCommand.class)))
                .thenReturn(productDto1);

        mvc.perform(post("/products/{productId}", productDto1.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").value(productDto1.getId()),
                        jsonPath("$.externalId").value(productDto1.getExternalId()),
                        jsonPath("$.title").value("Cucumber"));
    }

    @Test
    void ProductController_deleteProduct_ReturnsIdAndExternalIdOfDeletedDto() throws InvalidDtoException, Exception {
        ProductQuery productQuery = new ProductQuery();
        productQuery.setId("1");
        productQuery.setExternalId("101");

        when(productCommandHandler.handleDelete("1")).thenReturn(productQuery);

        mvc.perform(delete("/products/{productId}", 1)).andExpectAll(
                status().isOk(),
                jsonPath("$.id").value(productQuery.getId()),
                jsonPath("$.externalId").value(productQuery.getExternalId()));
    }

    @Test
    void ProductController_getAllPricesOfProductWithoutParam_ReturnsListWithPricesOfAllProducts() throws Exception {
        when(priceQueryHandler.getAll()).thenReturn(List.of(priceDto1, priceDto2));

        mvc.perform(get("/products/prices")).andExpectAll(
                status().isOk(),
                jsonPath("$.length()").value(2));
    }

    @Test
    void ProductController_getAllPricesOfProductWithParam_ReturnsListWithPricesOfOneProduct() throws Exception {
        priceDto2.setProductId("101");

        when(priceQueryHandler.getAllPricesOfProduct("101")).thenReturn(List.of(priceDto1, priceDto2));

        mvc.perform(get("/products/prices").param("p", "101")).andExpectAll(
                status().isOk(),
                jsonPath("$.length()").value(2),
                jsonPath("$[0].productId").value("101"),
                jsonPath("$[1].productId").value("101"));
    }

    @Test
    void ProductController_getOnePriceOfProduct_ReturnsOnePriceOfProductWith() throws Exception {
        when(priceQueryHandler.getOnePriceOfProduct(priceDto1.getId(), priceDto1.getProductId()))
                .thenReturn(priceDto1);

        mvc.perform(get("/products/{productId}/prices/{priceId}", priceDto1.getProductId(), priceDto1.getId()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(priceDto1.getId()),
                        jsonPath("$.productId").value(priceDto1.getProductId()));
    }

    @Test
    void ProductController_updatePrice_ReturnsUpdatedPriceWithAllFields() throws InvalidDtoException, Exception {
        priceDto1.setCurrency("dollar");
        priceDto1.setUnitAmount(200);
        priceDto1.setUnitAmountDecimal(200);
        priceDto1.setPurchasePrice(150);
        priceDto1.setSuggestedAmount(175);
        String jsonRequest = "{\"currency\":\"dollar\",\"unitAmount\":200,\"unitAmountDecimal\":200,\"purchasePrice\":175}";

        when(priceCommandHandler.handleUpdate(Mockito.anyString(), Mockito.any(PriceCommand.class)))
                .thenReturn(priceDto1);

        mvc.perform(post("/products/prices/{priceId}", priceDto1.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").value(priceDto1.getId()),
                        jsonPath("$.productId").value(priceDto1.getProductId()),
                        jsonPath("$.currency").value(priceDto1.getCurrency()),
                        jsonPath("$.unitAmount").value(priceDto1.getUnitAmount()),
                        jsonPath("$.unitAmountDecimal").value(priceDto1.getUnitAmountDecimal()),
                        jsonPath("$.purchasePrice").value(priceDto1.getPurchasePrice()),
                        jsonPath("$.suggestedAmount").value(priceDto1.getSuggestedAmount()));
    }

    @Test
    void ProductController_deletePrice_ReturnsDeletedPriceId() throws InvalidDtoException, Exception {
        when(priceCommandHandler.handleDelete(Mockito.anyString())).thenReturn(priceDto1.getId());

        String resultContent = mvc.perform(delete("/products/prices/{priceId}", priceDto1.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(resultContent).isEqualTo(priceDto1.getId());
    }

    @Test
    void ProductController_createPrice_ReturnsPriceWithAllFieldsAndSameProductId() throws InvalidPriceException, Exception {
        priceDto1.setCurrency("euro");
        priceDto1.setUnitAmount(15);
        priceDto1.setUnitAmountDecimal(15.5);
        priceDto1.setPurchasePrice(10);
        priceDto1.setSuggestedAmount(12.75);
        String jsonRequest = "{\"currency\":\"euro\",\"unitAmount\":15,\"unitAmountDecimal\":15.5,\"purchasePrice\":10}";

        when(priceCommandHandler.handleCreate(Mockito.any(PriceCommand.class), Mockito.anyString()))
                .thenReturn(priceDto1);

        mvc.perform(post("/products/{productId}/prices", priceDto1.getProductId()).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").value(priceDto1.getId()),
                        jsonPath("$.productId").value(priceDto1.getProductId()),
                        jsonPath("$.currency").value(priceDto1.getCurrency()),
                        jsonPath("$.unitAmount").value(priceDto1.getUnitAmount()),
                        jsonPath("$.unitAmountDecimal").value(priceDto1.getUnitAmountDecimal()),
                        jsonPath("$.purchasePrice").value(priceDto1.getPurchasePrice()),
                        jsonPath("$.suggestedAmount").value(priceDto1.getSuggestedAmount()));
    }
}

