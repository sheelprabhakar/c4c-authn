package com.c4c.authn.rest.controller;

import com.c4c.authn.rest.resource.AttributeResource;
import com.c4c.authn.rest.resource.PagedModelResponse;
import com.c4c.authn.utils.TestUtils;
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
    private static final String BASE_URL = API_V1 + ATTRIBUTE_URL;

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));

        this.mockMvc.perform(this.get(BASE_URL + "/" + "non exist")).andExpect(status().isBadRequest());

        string = this.mockMvc.perform(this.get(BASE_URL)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        HashMap<String, Object> restResourcePage =
                TestUtils.convertJsonStringToObject(string, new TypeReference<HashMap<String, Object>>() {
                });
        assertTrue(((List<AttributeResource>) restResourcePage.get("items")).size() > 0);
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
        resource.setName("");
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

    /**
     * Test tenant read ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test Attribute Read operation")
    void attributeReadOk() throws Exception {
        AttributeResource resource = Instancio.create(AttributeResource.class);
        String result = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        AttributeResource tenantResource = TestUtils.convertJsonStringToObject(result, AttributeResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + tenantResource.getId()))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(tenantResource.getName()));

        result = this.mockMvc.perform(this.get(BASE_URL))
                //.andDo(print())
                .  andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PagedModelResponse<AttributeResource>
                resourceList = TestUtils.convertJsonStringToObject(result, PagedModelResponse.class);
        Assertions.assertTrue(resourceList.getItems().size() > 0);

        result = this.mockMvc.perform(this.get(BASE_URL+"?pageSize=10&pageNo=0"))
                //.andDo(print())
                .  andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
                resourceList = TestUtils.convertJsonStringToObject(result, PagedModelResponse.class);
        Assertions.assertTrue(resourceList.getItems().size() > 0);
    }
}
