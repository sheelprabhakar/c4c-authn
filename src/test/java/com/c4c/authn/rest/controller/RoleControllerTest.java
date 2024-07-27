package com.c4c.authn.rest.controller;

import com.c4c.authn.rest.resource.RoleResource;
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
import static com.c4c.authn.common.Constants.ROLE_URL;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String BASE_URL = API_V1 + ROLE_URL;

    @Test
    @DisplayName("Create Role test")
    void testCreateNewRoleOK() throws Exception {
        RoleResource resource = Instancio.create(RoleResource.class);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));
    }

    @Test
    @DisplayName("Get By ID test")
    void testGetByIdOK() throws Exception {
        RoleResource resource = Instancio.create(RoleResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RoleResource resource1 = TestUtils.convertJsonStringToObject(string, RoleResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));

        this.mockMvc.perform(this.get(BASE_URL + "/" + "non exist")).andExpect(status().isBadRequest());

        string = this.mockMvc.perform(this.get(BASE_URL)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        HashMap<String, Object> roleResourcePage =
                TestUtils.convertJsonStringToObject(string, new TypeReference<HashMap<String, Object>>() {
                });
        assertTrue(((List<RoleResource>) roleResourcePage.get("content")).size() > 0);
    }

    @Test
    @DisplayName("Create new role test Bad request")
    void testCreateNewResource400() throws Exception {
        RoleResource resource = Instancio.create(RoleResource.class);
        resource.setName("");
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test update role")
    void testUpdateRoleOk() throws Exception {
        RoleResource resource = Instancio.create(RoleResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RoleResource resource1 = TestUtils.convertJsonStringToObject(string, RoleResource.class);
        this.mockMvc.perform(this.put(BASE_URL, resource1)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(resource1.getId().toString()));

    }

    @Test
    @DisplayName("Delete role test")
    void testDeleteRoleByIdOk() throws Exception {
        RoleResource resource = Instancio.create(RoleResource.class);
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn();
        String string = mvcResult.getResponse().getContentAsString();
        RoleResource resource1 = TestUtils.convertJsonStringToObject(string, RoleResource.class);
        this.mockMvc.perform(this.delete(BASE_URL + "/" + resource1.getId())).andExpect(status().isNoContent());

        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getId())).andExpect(status().isNotFound());
    }
}
