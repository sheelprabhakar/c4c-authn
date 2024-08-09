package com.c4c.authn.rest.controller;

import com.c4c.authn.TestcontainersConfiguration;
import com.c4c.authn.core.service.impl.JwtTokenProvider;
import com.c4c.authn.rest.resource.auth.JwtResponse;
import com.c4c.authn.utils.TestUtils;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static com.c4c.authn.common.Constants.AUTH_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Abstract integration test.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest extends TestcontainersConfiguration {
    //ToDo write idempotency tests
    public static final String TENANT_ID = "fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b";
    /**
     * The Mock mvc.
     */
    @Autowired
    MockMvc mockMvc;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;
    SecretKey secret;
    /**
     * The Jwt token provider.
     */
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    /**
     * The Token.
     */
    String token = "";

    /**
     * Init.
     */
    @PostConstruct
    void init() {
        secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gets admin token.
     *
     * @return the admin token
     * @throws Exception the exception
     */
    String getAdminToken() throws Exception {
        if (!StringUtils.hasLength(token)) {
            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .post(AUTH_URL + "/authenticate")
                            .content("{\"username\":\"sheel.prabhakar@gmail.com\"," +
                                    " \"password\":\"admin123\", \"isOtp\":false}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    //.andDo(print())
                    .andExpect(status().isOk()).andReturn();

            JwtResponse jwt = TestUtils
                    .convertJsonStringToObject(mvcResult.getResponse()
                            .getContentAsString(), JwtResponse.class);
            token = "Bearer " + jwt.getAccessToken();
        }
        return token;

    }

    /**
     * Post mock http servlet request builder.
     *
     * @param baseUrl  the base url
     * @param resource the resource
     * @return the mock http servlet request builder
     * @throws Exception the exception
     */
    MockHttpServletRequestBuilder post(final String baseUrl, Object resource) throws Exception {
        return MockMvcRequestBuilders
                .post(baseUrl)
                .content(TestUtils.convertObjectToJsonString(resource))
                .header("Authorization", getAdminToken())
                .header("X-TenantID", TENANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    MockHttpServletRequestBuilder get(final String url) throws Exception {
        return MockMvcRequestBuilders
                .get(url)
                .header("Authorization", getAdminToken())
                .header("X-TenantID", TENANT_ID)
                .accept(MediaType.APPLICATION_JSON);
    }

    MockHttpServletRequestBuilder delete(final String url) throws Exception {
        return MockMvcRequestBuilders
                .delete(url)
                .header("Authorization", getAdminToken())
                .header("X-TenantID", TENANT_ID)
                .accept(MediaType.APPLICATION_JSON);
    }

    /**
     * Put mock http servlet request builder.
     *
     * @param baseUrl  the base url
     * @param resource the resource
     * @return the mock http servlet request builder
     * @throws Exception the exception
     */
    MockHttpServletRequestBuilder put(final String baseUrl, Object resource) throws Exception {
        return MockMvcRequestBuilders
                .put(baseUrl)
                .content(TestUtils.convertObjectToJsonString(resource))
                .header("Authorization", getAdminToken())
                .header("X-TenantID", TENANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }
}
