package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.LOOKUP_URL;
import static com.c4c.authz.common.Constants.POLICY_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PolicyControllerTest extends AbstractIntegrationTest {
  /**
   * The Base url.
   */
  private final String BASE_URL = API_V1 + POLICY_URL;

  @Test
  @DisplayName("Test get policies ok")
  void testGetPoliciesForCurrentClient() throws Exception {
    this.mockMvc.perform(this.get(BASE_URL.replace("{tenantId}", TENANT_ID) +"/me"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(250));
  }
}