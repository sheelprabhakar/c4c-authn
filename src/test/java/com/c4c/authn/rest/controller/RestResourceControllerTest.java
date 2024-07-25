package com.c4c.authn.rest.controller;

import com.c4c.authn.rest.resource.RestResource;
import com.c4c.authn.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.List;

import static com.c4c.authn.common.Constants.API_V1;
import static com.c4c.authn.common.Constants.REST_RESOURCE_URL;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestResourceControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String BASE_URL = API_V1 + REST_RESOURCE_URL;

    @Test
    @DisplayName("Create Rest resource test")
    void testCreateNewResourceOK() throws Exception {
        RestResource resource = Instancio.create(RestResource.class);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeName").value(resource.getAttributeName()));
    }

    @Test
    @DisplayName("Get By ID test")
    void testGetByIdOK() throws Exception {
        RestResource resource = Instancio.create(RestResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RestResource resource1 = TestUtils.convertJsonStringToObject(string, RestResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeName").value(resource.getAttributeName()));

        this.mockMvc.perform(this.get(BASE_URL + "/" + "non exist")).andExpect(status().isBadRequest());

        string = this.mockMvc.perform(this.get(BASE_URL)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        HashMap<String, Object> restResourcePage =
                TestUtils.convertJsonStringToObject(string, new TypeReference<HashMap<String, Object>>() {
                });
        assertTrue(((List<RestResource>)restResourcePage.get("content")).size() > 0);
    }

    @Test
    @DisplayName("Create Rest resource test Bad request")
    void testCreateNewResource400() throws Exception {
        RestResource resource = Instancio.create(RestResource.class);
        resource.setAttributeName("");
        resource.setPath(null);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update Test resource")
    void testUpdateRestResourceOk() throws Exception {
        RestResource resource = Instancio.create(RestResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RestResource resource1 = TestUtils.convertJsonStringToObject(string, RestResource.class);
        this.mockMvc.perform(this.put(BASE_URL, resource1)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(resource1.getId().toString()));

    }

    @Test
    @DisplayName("Delete Test resource")
    void testDeleteRestResourceByIdOk() throws Exception {
        RestResource resource = Instancio.create(RestResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RestResource resource1 = TestUtils.convertJsonStringToObject(string, RestResource.class);
        this.mockMvc.perform(this.delete(BASE_URL+"/"+resource1.getId())).andExpect(status().isNoContent());

        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isNotFound());
    }
}
