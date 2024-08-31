package com.c4c.authz.rest.controller;

import com.c4c.authz.common.JsonUtils;
import com.c4c.authz.rest.resource.ClientResource;
import com.c4c.authz.rest.resource.auth.JwtResponse;
import com.c4c.authz.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.AUTH_URL;
import static com.c4c.authz.common.Constants.CLIENT_CREDENTIALS;
import static com.c4c.authz.common.Constants.CLIENT_URL;
import static com.c4c.authz.common.Constants.POLICY_URL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Policy controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PolicyControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String baseUrl = API_V1 + POLICY_URL;

    /**
     * Test get policies for current user.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test get policies ok for user")
    void testGetPoliciesForCurrentUser() throws Exception {
        this.mockMvc.perform(this.get(baseUrl.replace("{tenantId}", TENANT_ID) + "/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(20));
    }

    /**
     * Test get policies for current client.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test get policies ok for client")
    void testGetPoliciesForCurrentClient() throws Exception {
        String baseUrl1 = API_V1 + CLIENT_URL;
        MvcResult result = this.mockMvc.perform(this.post(baseUrl1 + "?name=test_client_auth", null))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test_client_auth")).andReturn();
        ClientResource clientResource = JsonUtils.convertJsonStringToObject(
                result.getResponse().getContentAsString(), ClientResource.class);
        String requestParam = String.format(CLIENT_CRED_AUTH_PARAMS,
                clientResource.getTenantId().toString(), clientResource.getClientId(), clientResource.getClientSecret(),
                CLIENT_CREDENTIALS);

        String response = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(AUTH_URL + "/" + requestParam)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        JwtResponse jwtResponse = TestUtils.convertJsonStringToObject(response, JwtResponse.class);
        assertNotNull(jwtResponse.getAccessToken());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(baseUrl.replace("{tenantId}", TENANT_ID) + "/client")
                        .header("Authorization", "Bearer "+jwtResponse.getAccessToken())
                        .header("X-TenantID", clientResource.getTenantId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));

    }
}