package com.example.shop;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/** The one test that does boot the whole stack, wiring included. */
@SpringBootTest(properties = "shop.kafka.enabled=false")
@AutoConfigureMockMvc
class PlaceOrderEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper json;

    private static final String BODY = """
            {
              "customerId": "cust-1",
              "lines": [
                { "sku": "SKU-1", "quantity": 2, "unitPrice": 19.99 }
              ]
            }
            """;

    private static final String TOO_EXPENSIVE = """
            {
              "customerId": "cust-1",
              "lines": [
                { "sku": "SKU-1", "quantity": 100, "unitPrice": 50.00 }
              ]
            }
            """;

    @Test
    void create_findById() throws Exception {
        MvcResult created = mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CONFIRMED"))
                .andExpect(jsonPath("$.total").value("39.98"))
                .andReturn();

        JsonNode node = json.readTree(created.getResponse().getContentAsString());
        String id = node.get("id").asText();

        mockMvc.perform(get("/orders/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("cust-1"));
    }

    @Test
    void create_returns_402_when_the_gateway_declines() throws Exception {
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(TOO_EXPENSIVE))
                .andExpect(status().isPaymentRequired());
    }
}
