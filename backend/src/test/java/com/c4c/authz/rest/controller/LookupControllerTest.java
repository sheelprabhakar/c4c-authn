package com.c4c.authz.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.LOOKUP_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Lookup controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LookupControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String BASE_URL = API_V1 + LOOKUP_URL;

    /**
     * Test get countries ok.
     *
     * @throws Exception the exception
     */
    @Test
    void test_get_countries_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/country")
                        .header("Authorization", getAdminToken())
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(250));
    }

    /**
     * Test get states ok.
     *
     * @throws Exception the exception
     */
    @Test
    void test_get_states_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/country/101/state")
                        .header("Authorization", getAdminToken())
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(37));
    }

    /**
     * Test get cities ok.
     *
     * @throws Exception the exception
     */
    @Test
    void test_get_cities_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/state/493/city")
                        .header("Authorization", getAdminToken())
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }
}
