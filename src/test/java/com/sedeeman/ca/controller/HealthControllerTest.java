package com.sedeeman.ca.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
@DisplayName("Health Controller Tests")
class HealthControllerTest {

    @MockBean
    private HealthController healthController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(healthController).build();
    }

    @Test
    @DisplayName("Test Check Health - Up")
    void testCheckHealthUp() throws Exception {
        when(healthController.health()).thenReturn(Health.up().build());

        mockMvc.perform(get("/health")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Check Health - Down")
    void testCheckHealthDown() throws Exception {
        when(healthController.health()).thenReturn(Health.down().build());

        mockMvc.perform(get("/health"))
                .andExpect(content().string(""));
    }
}

