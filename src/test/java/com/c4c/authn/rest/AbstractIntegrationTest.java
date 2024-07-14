package com.c4c.authn.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.authn.AuthnApplication;
import com.c4c.authn.TestcontainersConfiguration;
import com.c4c.authn.config.security.JwtTokenProvider;
import com.c4c.authn.rest.resource.auth.JwtResponse;
import com.c4c.authn.utils.TestUtils;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * The type Abstract integration test.
 */
@SpringBootTest(classes = AuthnApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Testcontainers
public abstract class AbstractIntegrationTest {
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
              .post("/api/v1/auth/authenticate")
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
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);
  }

  MockHttpServletRequestBuilder get(final String url) throws Exception {
    return MockMvcRequestBuilders
        .get(url)
        .header("Authorization", getAdminToken())
        .accept(MediaType.APPLICATION_JSON);
  }

  MockHttpServletRequestBuilder delete(final String url) throws Exception {
    return MockMvcRequestBuilders
        .delete(url)
        .header("Authorization", getAdminToken())
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
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);
  }
}
