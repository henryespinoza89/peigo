package com.peigo.msvcaccounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peigo.msvcaccounts.common.route.Route;
import com.peigo.msvcaccounts.domain.entity.Client;
import com.peigo.msvcaccounts.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class)
@OverrideAutoConfiguration(enabled = true)
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() { objectMapper = new ObjectMapper(); }

    @Test
    @DisplayName("create client")
    void createClient() throws Exception {
        String url = Route.Client.CREATE_CIENT;
        // Given
        Client client = new Client(null, "Henry", "Espinoza", "46887234");
        when(clientService.createClient(any())).then(invocation -> {
           Client c = invocation.getArgument(0);
           c.setIdClient(3L);
           return c;
        });
        // When
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.idClient", is(3)))
                .andExpect((ResultMatcher) jsonPath("$.firstName", is("Henry")))
                .andExpect((ResultMatcher) jsonPath("$.lastName", is("Espinoza")))
                .andExpect((ResultMatcher) jsonPath("$.dni", is("46887234")));
        verify(clientService).createClient(any());
    }
}
