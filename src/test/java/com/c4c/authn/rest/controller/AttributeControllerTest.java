package com.c4c.authn.rest.controller;

import com.c4c.authn.rest.resource.AttributeResource;
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
import static com.c4c.authn.common.Constants.ATTRIBUTE_URL;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Attribute controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AttributeControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String BASE_URL = API_V1 + ATTRIBUTE_URL;

    /**
     * Test create new resource ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create Rest resource test")
    void testCreateNewResourceOK() throws Exception {
        AttributeResource resource = Instancio.create(AttributeResource.class);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeName").value(resource.getAttributeName()));
    }

    /**
     * Test get by id ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Get By ID test")
    void testGetByIdOK() throws Exception {
        AttributeResource resource = Instancio.create(AttributeResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        AttributeResource resource1 = TestUtils.convertJsonStringToObject(string, AttributeResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeName").value(resource.getAttributeName()));

        this.mockMvc.perform(this.get(BASE_URL + "/" + "non exist")).andExpect(status().isBadRequest());

        string = this.mockMvc.perform(this.get(BASE_URL)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        HashMap<String, Object> restResourcePage =
                TestUtils.convertJsonStringToObject(string, new TypeReference<HashMap<String, Object>>() {
                });
        assertTrue(((List<AttributeResource>) restResourcePage.get("content")).size() > 0);
    }

    /**
     * Test create new resource 400.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create Rest resource test Bad request")
    void testCreateNewResource400() throws Exception {
        AttributeResource resource = Instancio.create(AttributeResource.class);
        resource.setAttributeName("");
        resource.setPath(null);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Test update attribute resource ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Update Test resource")
    void testUpdateAttributeResourceOk() throws Exception {
        AttributeResource resource = Instancio.create(AttributeResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        AttributeResource resource1 = TestUtils.convertJsonStringToObject(string, AttributeResource.class);
        this.mockMvc.perform(this.put(BASE_URL, resource1)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(resource1.getId().toString()));

    }

    /**
     * Test delete attribute resource by id ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Delete Test resource")
    void testDeleteAttributeResourceByIdOk() throws Exception {
        AttributeResource resource = Instancio.create(AttributeResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        AttributeResource resource1 = TestUtils.convertJsonStringToObject(string, AttributeResource.class);
        this.mockMvc.perform(this.delete(BASE_URL + "/" + resource1.getId())).andExpect(status().isNoContent());

        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isNotFound());
    }
}
