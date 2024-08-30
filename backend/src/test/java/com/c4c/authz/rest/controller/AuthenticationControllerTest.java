package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.AUTH_URL;
import static com.c4c.authz.common.Constants.CLIENT_CREDENTIALS;
import static com.c4c.authz.common.Constants.CLIENT_URL;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.authz.common.JsonUtils;
import com.c4c.authz.rest.resource.ClientResource;
import com.c4c.authz.rest.resource.auth.JwtResponse;
import com.c4c.authz.utils.TestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * The type Authentication controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthenticationControllerTest extends AbstractIntegrationTest {
    /**
     * The constant MOBILE.
     */
    static final String MOBILE = "9898989898";
   /**
     * The Base url.
     */
    private final String BASE_URL = AUTH_URL;

    /**
     * Test authenticate ok.
     *
     * @throws Exception the exception
     */
    @Test
    void test_authenticate_ok() throws Exception {
        assertTrue(this.getAdminToken().startsWith("Bearer"));

        String response = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{\"username\":\"admin@c4c.com\"," +
                                " \"password\":\"admin123\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        JwtResponse jwtResponse = TestUtils.convertJsonStringToObject(response, JwtResponse.class);
        assertNotNull(jwtResponse.getAccessToken());
        assertNotNull(jwtResponse.getRefreshToken());
        Claims payload = Jwts.parser().verifyWith(this.secret).build()
                .parseSignedClaims(jwtResponse.getAccessToken()).getPayload();
        assertEquals("admin@c4c.com", payload.getSubject());
        List<Map<String, String>> authorities = (List<Map<String, String>>) payload.get("authorities");
        assertEquals(2, authorities.size());
    }

    @Test
    @DisplayName("Test client credential authentication flow")
    void test_client_authenticate_ok() throws Exception {

        String BASE_URL1 = API_V1 + CLIENT_URL;
        MvcResult result = this.mockMvc.perform(this.post(BASE_URL1 + "?name=test_client_auth", null))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test_client_auth")).andReturn();
        ClientResource clientResource = JsonUtils.convertJsonStringToObject(
                result.getResponse().getContentAsString(), ClientResource.class);
        String requestParam = String.format(CLIENT_CRED_AUTH_PARAMS,
                clientResource.getTenantId().toString(), clientResource.getClientId(),clientResource.getClientSecret(),
                CLIENT_CREDENTIALS);

        String response = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/"+requestParam)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        JwtResponse jwtResponse = TestUtils.convertJsonStringToObject(response, JwtResponse.class);
        assertNotNull(jwtResponse.getAccessToken());
        assertNull(jwtResponse.getRefreshToken());
        Claims payload = Jwts.parser().verifyWith(this.secret).build()
                .parseSignedClaims(jwtResponse.getAccessToken()).getPayload();
        assertEquals(clientResource.getClientId(), payload.getSubject());
        List<Map<String, String>> authorities = (List<Map<String, String>>) payload.get("authorities");
        assertEquals(1, authorities.size());
    }

    /**
     * Test authenticate wrong credentials.
     *
     * @throws Exception the exception
     */
    @Test
    void test_authenticate_wrong_credentials() throws Exception {
        String msg = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{\"username\":\"admin@c4c.com\"," +
                                " \"password\":\"admin1234\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        assertEquals("Invalid credentials", msg);

        msg = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{\"username\":\"sheel.prabhakar123@gmail.com\"," +
                                " \"password\":\"admin123\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        assertEquals("Invalid credentials", msg);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{ \"password\":\"admin123\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("Invalid request content."));

    }

    /**
     * Test logout ok.
     *
     * @throws Exception the exception
     */
    @Test
    void test_logout_ok() throws Exception {
        String token = getAdminToken();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/logout")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/logout")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * Test refresh token ok.
     *
     * @throws Exception the exception
     */
    @Test
    void test_refresh_token_ok() throws Exception {
        assertTrue(this.getAdminToken().startsWith("Bearer"));

        String response = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{\"username\":\"admin@c4c.com\"," +
                                " \"password\":\"admin123\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        JwtResponse jwtResponse = TestUtils.convertJsonStringToObject(response, JwtResponse.class);
        assertNotNull(jwtResponse.getAccessToken());
        Claims payload = Jwts.parser().verifyWith(this.secret).build()
                .parseSignedClaims(jwtResponse.getAccessToken()).getPayload();
        assertEquals("admin@c4c.com", payload.getSubject());
        List<Map<String, String>> authorities = (List<Map<String, String>>) payload.get("authorities");
        assertEquals(2, authorities.size());
        assertNotNull(jwtResponse.getRefreshToken());


        response = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/refreshToken")
                        .param("refreshToken", jwtResponse.getRefreshToken())
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        jwtResponse = TestUtils.convertJsonStringToObject(response, JwtResponse.class);
        assertNotNull(jwtResponse.getAccessToken());
        payload = Jwts.parser().verifyWith(secret).build()
                .parseSignedClaims(jwtResponse.getAccessToken()).getPayload();
        assertEquals("admin@c4c.com", payload.getSubject());
        authorities = (List<Map<String, String>>) payload.get("authorities");
        assertEquals(2, authorities.size());
        assertNotNull(jwtResponse.getRefreshToken());

    }
}
