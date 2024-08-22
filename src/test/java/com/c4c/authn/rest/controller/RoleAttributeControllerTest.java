package com.c4c.authn.rest.controller;

import com.c4c.authn.rest.resource.AttributeResource;
import com.c4c.authn.rest.resource.RoleAttributeResource;
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
import java.util.UUID;

import static com.c4c.authn.common.Constants.API_V1;
import static com.c4c.authn.common.Constants.ATTRIBUTE_URL;
import static com.c4c.authn.common.Constants.ROLE_ATTRIBUTE_URL;
import static com.c4c.authn.common.Constants.ROLE_URL;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleAttributeControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String BASE_URL = API_V1 + ROLE_ATTRIBUTE_URL;

    private UUID addAttribute() throws Exception {
        AttributeResource attributeResource = Instancio.create(AttributeResource.class);
        String content = this.mockMvc.perform(this.post(API_V1 + ATTRIBUTE_URL, attributeResource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        attributeResource = TestUtils.convertJsonStringToObject(content, AttributeResource.class);
        return attributeResource.getId();
    }

    private UUID addRole() throws Exception {
        RoleResource resource = Instancio.create(RoleResource.class);
        String content = this.mockMvc.perform(this.post(API_V1 + ROLE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        RoleResource roleResource = TestUtils.convertJsonStringToObject(content, RoleResource.class);
        return roleResource.getId();
    }

    @Test
    @DisplayName("Create role attribute test")
    void testCreateNewUserRoleOK() throws Exception {
        UUID attributeId = this.addAttribute();
        UUID roleId = this.addRole();
        RoleAttributeResource resource = RoleAttributeResource.builder().attributeId(attributeId).roleId(roleId).build();
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeId").value(resource.getAttributeId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canCreate").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canRead").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canDelete").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canUpdate").value(resource.isCanCreate()));

        attributeId = this.addAttribute();
        roleId = this.addRole();
        resource = RoleAttributeResource.builder().attributeId(attributeId).roleId(roleId).canCreate(true)
                .canDelete(true).canRead(true).canUpdate(true).build();
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeId").value(resource.getAttributeId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canCreate").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canRead").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canDelete").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canUpdate").value(resource.isCanCreate()));
    }

    @Test
    @DisplayName("Get By ID test")
    void testGetByIdOK() throws Exception {
        UUID attributeId = this.addAttribute();
        UUID roleId = this.addRole();
        RoleAttributeResource resource = RoleAttributeResource.builder().attributeId(attributeId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeId").value(resource.getAttributeId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        RoleAttributeResource resource1 = TestUtils.convertJsonStringToObject(string, RoleAttributeResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getRoleId() + "/" + resource1.getAttributeId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeId").value(resource.getAttributeId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()));

        this.mockMvc.perform(this.get(BASE_URL + "/" + "non exist" + "/" + "Not Exist"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(
                        this.get(BASE_URL + "/" + UUID.randomUUID().toString() + "/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        string = this.mockMvc.perform(this.get(BASE_URL)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        HashMap<String, Object> roleResourcePage =
                TestUtils.convertJsonStringToObject(string, new TypeReference<HashMap<String, Object>>() {
                });
        assertTrue(((List<RoleAttributeResource>) roleResourcePage.get("items")).size() > 0);
    }

    @Test
    @DisplayName("Create User Role test Bad request")
    void testCreateNewResource400() throws Exception {
        RoleAttributeResource resource = Instancio.create(RoleAttributeResource.class);
        resource.setRoleId(null);
        resource.setAttributeId(null);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test update user role")
    void testUpdateUserRoleOk() throws Exception {
        UUID attributeId = this.addAttribute();
        UUID roleId = this.addRole();
        RoleAttributeResource resource = RoleAttributeResource.builder().attributeId(attributeId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeId").value(resource.getAttributeId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        RoleAttributeResource resource1 = TestUtils.convertJsonStringToObject(string, RoleAttributeResource.class);

        this.mockMvc.perform(this.put(BASE_URL, resource1)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeId").value(resource.getAttributeId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()));

    }

    @Test
    @DisplayName("Delete role attribute test")
    void testDeleteUserRoleByIdOk() throws Exception {
        UUID attributeId = this.addAttribute();
        UUID roleId = this.addRole();
        RoleAttributeResource resource = RoleAttributeResource.builder().attributeId(attributeId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeId").value(resource.getAttributeId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        RoleAttributeResource resource1 = TestUtils.convertJsonStringToObject(string, RoleAttributeResource.class);
        this.mockMvc.perform(this.delete(BASE_URL + "/" + resource1.getRoleId()+ "/" + resource1.getAttributeId()))
                .andExpect(status().isNoContent());


        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getRoleId() + "/" + resource1.getAttributeId()))
                .andExpect(status().isNotFound());
    }
}
