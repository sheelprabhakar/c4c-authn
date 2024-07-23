package com.c4c.authn.rest.controller;

import static com.c4c.authn.common.Constants.API_V1;
import static com.c4c.authn.common.Constants.USER_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.authn.rest.resource.UserResource;
import com.c4c.authn.utils.TestUtils;
import com.c4c.authn.utils.UserResourceHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * The type User controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserControllerTest extends AbstractIntegrationTest {
  /**
   * The constant MOBILE.
   */
  static final String MOBILE = "9898989898";
  /**
   * The Base url.
   */
  private final String BASE_URL = API_V1 + USER_URL;

  /**
   * The Unq.
   */
  private final int unq = 0;

  /**
   * Test add user ok.
   *
   * @throws Exception the exception
   */
  @Test
  void test_add_user_ok() throws Exception {
    UserResource resource = UserResourceHelper.getNew(null);
    this.mockMvc.perform(this.post(BASE_URL, resource))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(resource.getMobile()));
  }

  /**
   * Test update user ok.
   *
   * @throws Exception the exception
   */
  @Test
  void test_update_user_ok() throws Exception {
    UserResource resource = UserResourceHelper.getNew(null);
    MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
        //.andDo(print())
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(resource.getMobile())).andReturn();
    UserResource userResource = TestUtils
        .convertJsonStringToObject(mvcResult.getResponse()
            .getContentAsString(), UserResource.class);
    userResource.setMobile("1234567890");
    this.mockMvc.perform(this.put(BASE_URL, userResource))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value("1234567890"));
  }
}
