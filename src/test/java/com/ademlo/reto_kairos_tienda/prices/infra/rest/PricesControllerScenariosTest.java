package com.ademlo.reto_kairos_tienda.prices.infra.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestPriceRepositoryConfig.class)
class PricesControllerScenariosTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void scenario1_requestAt2020_06_14_10_00_returnsPriceList1() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.5))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.found").value(true));
    }

    @Test
    void scenario2_requestAt2020_06_14_16_00_returnsPriceList2_highestPriority() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-06-14T16:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.found").value(true));
    }

    @Test
    void scenario3_requestAt2020_06_14_21_00_returnsPriceList1() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-06-14T21:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.5))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.found").value(true));
    }

    @Test
    void scenario4_requestAt2020_06_15_10_00_returnsPriceList3() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-06-15T10:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00"))
                .andExpect(jsonPath("$.price").value(30.5))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.found").value(true));
    }

    @Test
    void scenario5_requestAt2020_06_16_21_00_returnsPriceList4() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-06-16T21:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.found").value(true));
    }

    @Test
    void boundary_applicationDateEqualsStartDate_isApplicable() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-06-15T00:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.found").value(true));
    }

    @Test
    void boundary_applicationDateEqualsEndDate_isApplicable() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-06-14T18:30:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.found").value(true));
    }

    @Test
    void invalidDateFormat_returns400() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "14-06-2020 10:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void missingRequiredParameters_returns400() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-06-14T10:00:00"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void noPriceFound_returns404_withErrorBody() throws Exception {
        mockMvc.perform(get("/api/v1/prices/applicable")
                        .queryParam("applicationDate", "2020-01-01T10:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}

