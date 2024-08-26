// src/test/java/com/c4c/authz/rest/controller/ClientControllerTest.java
package com.c4c.authz.rest.controller;

import com.c4c.authz.rest.resource.ClientResource;
import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.List;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.CLIENT_URL;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Client controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientControllerTest extends AbstractIntegrationTest {
  /**
   * The constant BASE_URL.
   */
  private static final String BASE_URL = API_V1 + CLIENT_URL;

  /**
   * Test create new client ok.
   *
   * @throws Exception the exception
   */
  @Test
    @DisplayName("Create Client test")
    void testCreateNewClientOK() throws Exception {
        ClientResource resource = Instancio.create(ClientResource.class);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));
    }

  /**
   * Test get by id ok.
   *
   * @throws Exception the exception
   */
  @Test
    @DisplayName("Get Client By ID test")
    void testGetByIdOK() throws Exception {
        ClientResource resource = Instancio.create(ClientResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        ClientResource resource1 = TestUtils.convertJsonStringToObject(string, ClientResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));

        this.mockMvc.perform(this.get(BASE_URL + "/" + "non-exist")).andExpect(status().isBadRequest());

        string = this.mockMvc.perform(this.get(BASE_URL)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        HashMap<String, Object> clientResourcePage =
                TestUtils.convertJsonStringToObject(string, new TypeReference<HashMap<String, Object>>() {
                });
        assertTrue(((List<ClientResource>) clientResourcePage.get("items")).size() > 0);
    }

  /**
   * Test create new resource 400.
   *
   * @throws Exception the exception
   */
  @Test
    @DisplayName("Create new client test Bad request")
    void testCreateNewResource400() throws Exception {
        ClientResource resource = Instancio.create(ClientResource.class);
        resource.setName("");
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

  /**
   * Test update client ok.
   *
   * @throws Exception the exception
   */
  @Test
    @DisplayName("Test update client")
    void testUpdateClientOk() throws Exception {
        ClientResource resource = Instancio.create(ClientResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        ClientResource resource1 = TestUtils.convertJsonStringToObject(string, ClientResource.class);
        this.mockMvc.perform(this.put(BASE_URL, resource1)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(resource1.getId().toString()));
    }

  /**
   * Test delete client by id ok.
   *
   * @throws Exception the exception
   */
  @Test
    @DisplayName("Delete client test")
    void testDeleteClientByIdOk() throws Exception {
        ClientResource resource = Instancio.create(ClientResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        ClientResource resource1 = TestUtils.convertJsonStringToObject(string, ClientResource.class);
        this.mockMvc.perform(this.delete(BASE_URL + "/" + resource1.getId())).andExpect(status().isNoContent());

        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isNotFound());
    }

  /**
   * Client read ok.
   *
   * @throws Exception the exception
   */
  @Test
    @DisplayName("Test Client Read operation")
    void clientReadOk() throws Exception {
        ClientResource resource = Instancio.create(ClientResource.class);
        String result = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ClientResource clientResource = TestUtils.convertJsonStringToObject(result, ClientResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + clientResource.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(clientResource.getName()));

        result = this.mockMvc.perform(this.get(BASE_URL))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PagedModelResponse<ClientResource> resourceList = TestUtils.convertJsonStringToObject(result, PagedModelResponse.class);
        Assertions.assertTrue(resourceList.getItems().size() > 0);

        result = this.mockMvc.perform(this.get(BASE_URL + "?pageSize=10&pageNo=0"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        resourceList = TestUtils.convertJsonStringToObject(result, PagedModelResponse.class);
        Assertions.assertTrue(resourceList.getItems().size() > 0);
    }
}