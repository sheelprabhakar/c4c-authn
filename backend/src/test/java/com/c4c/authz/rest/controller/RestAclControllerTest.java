package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.REST_ACL_URL;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.authz.rest.resource.RestAclResource;
import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * The type Rest acl controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestAclControllerTest extends AbstractIntegrationTest {
    /**
     * The constant BASE_URL.
     */
    private static final String BASE_URL = API_V1 + REST_ACL_URL;

    /**
     * Test create new resource ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create Rest resource test")
    void testCreateNewResourceOK() throws Exception {
        RestAclResource resource = Instancio.create(RestAclResource.class);
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
        RestAclResource resource = Instancio.create(RestAclResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RestAclResource resource1 = TestUtils.convertJsonStringToObject(string, RestAclResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));

        this.mockMvc.perform(this.get(BASE_URL + "/" + "non exist")).andExpect(status().isBadRequest());

        string = this.mockMvc.perform(this.get(BASE_URL)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        HashMap<String, Object> restResourcePage =
                TestUtils.convertJsonStringToObject(string, new TypeReference<HashMap<String, Object>>() {
                });
        assertTrue(((List<RestAclResource>) restResourcePage.get("items")).size() > 0);
    }

    /**
     * Test create new rest acl 400.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create Rest resource test Bad request")
    void testCreateNewRestAcl400() throws Exception {
        RestAclResource resource = Instancio.create(RestAclResource.class);
        resource.setName("");
        resource.setPath(null);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Test update rest acl resource ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Update Test resource")
    void testUpdateRestAclResourceOk() throws Exception {
        RestAclResource resource = Instancio.create(RestAclResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RestAclResource resource1 = TestUtils.convertJsonStringToObject(string, RestAclResource.class);
        this.mockMvc.perform(this.put(BASE_URL, resource1)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(resource1.getId().toString()));

    }

    /**
     * Test delete rest acl resource by id ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Delete Test resource")
    void testDeleteRestAclResourceByIdOk() throws Exception {
        RestAclResource resource = Instancio.create(RestAclResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RestAclResource resource1 = TestUtils.convertJsonStringToObject(string, RestAclResource.class);
        this.mockMvc.perform(this.delete(BASE_URL + "/" + resource1.getId())).andExpect(status().isNoContent());

        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isNotFound());
    }

    /**
     * Rest acl read ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test rest acl Read operation")
    void restAclReadOk() throws Exception {
        RestAclResource resource = Instancio.create(RestAclResource.class);
        String result = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        RestAclResource aclResource = TestUtils.convertJsonStringToObject(result, RestAclResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + aclResource.getId()))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(aclResource.getName()));

        result = this.mockMvc.perform(this.get(BASE_URL))
                //.andDo(print())
                .  andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PagedModelResponse<RestAclResource>
                resourceList = TestUtils.convertJsonStringToObject(result, PagedModelResponse.class);
        Assertions.assertTrue(resourceList.getItems().size() > 0);

        result = this.mockMvc.perform(this.get(BASE_URL+"?pageSize=10&pageIndex=0"))
                //.andDo(print())
                .  andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
                resourceList = TestUtils.convertJsonStringToObject(result, PagedModelResponse.class);
        Assertions.assertTrue(resourceList.getItems().size() > 0);
    }
}
