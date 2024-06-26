package com.c4c.authn.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.authn.rest.resource.TenantResource;
import com.c4c.authn.utils.TenantResourceHelper;
import com.c4c.authn.utils.TestUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * The type Tenant controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TenantControllerTest extends AbstractIntegrationTest {
  /**
   * The Base url.
   */
  private final String BASE_URL = "/api/v1/tenant";

  /**
   * Test create tenant ok.
   *
   * @throws Exception the exception
   */
  @Test
  void test_create_tenant_ok() throws Exception {
    TenantResource resource = TenantResourceHelper.getNew();
    this.mockMvc.perform(this.post(BASE_URL, resource))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));
  }

  /**
   * Test create tenant duplicate.
   *
   * @throws Exception the exception
   */
  @Test
  void test_create_tenant_duplicate() throws Exception {
    TenantResource resource = TenantResourceHelper.getNew();
    String result = this.mockMvc.perform(this.post(BASE_URL, resource))
        //.andDo(print())
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    TenantResource tenantResource = TestUtils.convertJsonStringToObject(result, TenantResource.class);
    resource = TenantResourceHelper.getNew();
    resource.setName(tenantResource.getName());

    this.mockMvc.perform(this.post(BASE_URL, resource))
        //.andDo(print())
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString();
  }

  /**
   * Test create tenant validation.
   *
   * @throws Exception the exception
   */
  @Test
  void test_create_tenant_validation() throws Exception {
    TenantResource resource = TenantResourceHelper.getNew();
    this.mockMvc.perform(MockMvcRequestBuilders
            .post(BASE_URL)
            .content(TestUtils.convertObjectToJsonString(resource))
            .header("Authorization", "sdfsdffhsgj")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        //.andDo(print())
        .andExpect(status().isUnauthorized());

    resource.setCityId(-1);
    resource.setEmail(null);
    this.mockMvc.perform(this.post(BASE_URL, resource))
        //.andDo(print())
        .andExpect(status().isBadRequest());
  }

  /**
   * Test tenant update ok.
   *
   * @throws Exception the exception
   */
  @Test
  void test_tenant_update_ok() throws Exception {
    TenantResource resource = TenantResourceHelper.getNew();
    String result = this.mockMvc.perform(this.post(BASE_URL, resource))
        //.andDo(print())
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    TenantResource tenantResource = TestUtils.convertJsonStringToObject(result, TenantResource.class);
    tenantResource.setName(tenantResource.getName() + "dup");

    this.mockMvc.perform(this.put(BASE_URL, tenantResource))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(tenantResource.getName()));

    tenantResource.setName(null);

    this.mockMvc.perform(this.put(BASE_URL, tenantResource))
        //.andDo(print())
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString();
  }

  /**
   * Test tenant read ok.
   *
   * @throws Exception the exception
   */
  @Test
  void test_tenant_read_ok() throws Exception {
    TenantResource resource = TenantResourceHelper.getNew();
    String result = this.mockMvc.perform(this.post(BASE_URL, resource))
        //.andDo(print())
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    TenantResource tenantResource = TestUtils.convertJsonStringToObject(result, TenantResource.class);
    this.mockMvc.perform(this.get(BASE_URL + "/" + tenantResource.getId()))
        //.andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(tenantResource.getName()));

    result = this.mockMvc.perform(this.get(BASE_URL))
        //.andDo(print())
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    List<TenantResource> resourceList = new ArrayList<>();
    resourceList = TestUtils.convertJsonStringToObject(result, resourceList.getClass());
    assertTrue(resourceList.size() > 0);
  }
}
