package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.REST_ACL_URL;
import static com.c4c.authz.common.Constants.ROLE_REST_ACL_URL;
import static com.c4c.authz.common.Constants.ROLE_URL;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.authz.rest.resource.RestAclResource;
import com.c4c.authz.rest.resource.RoleRestAclResource;
import com.c4c.authz.rest.resource.RoleResource;
import com.c4c.authz.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * The type Role rest acl controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRestAclControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String BASE_URL = API_V1 + ROLE_REST_ACL_URL;

    /**
     * Add attribute uuid.
     *
     * @return the uuid
     * @throws Exception the exception
     */
    private UUID addAttribute() throws Exception {
        RestAclResource restAclResource = Instancio.create(RestAclResource.class);
        String content = this.mockMvc.perform(this.post(API_V1 + REST_ACL_URL, restAclResource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        restAclResource = TestUtils.convertJsonStringToObject(content, RestAclResource.class);
        return restAclResource.getId();
    }

    /**
     * Add role uuid.
     *
     * @return the uuid
     * @throws Exception the exception
     */
    private UUID addRole() throws Exception {
        RoleResource resource = Instancio.create(RoleResource.class);
        String content = this.mockMvc.perform(this.post(API_V1 + ROLE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        RoleResource roleResource = TestUtils.convertJsonStringToObject(content, RoleResource.class);
        return roleResource.getId();
    }

    /**
     * Test create new user role ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create role attribute test")
    void testCreateNewUserRoleOK() throws Exception {
        UUID restAclId = this.addAttribute();
        UUID roleId = this.addRole();
        RoleRestAclResource resource = RoleRestAclResource.builder().restAclId(restAclId).roleId(roleId).build();
        this.mockMvc.perform(this.post( BASE_URL.replace("{roleId}", roleId.toString()), resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restAclId").value(resource.getRestAclId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canCreate").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canRead").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canDelete").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canUpdate").value(resource.isCanCreate()));

        restAclId = this.addAttribute();
        roleId = this.addRole();
        resource = RoleRestAclResource.builder().restAclId(restAclId).roleId(roleId).canCreate(true)
                .canDelete(true).canRead(true).canUpdate(true).build();
        this.mockMvc.perform(this.post(BASE_URL.replace("{roleId}", roleId.toString()), resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restAclId").value(resource.getRestAclId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canCreate").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canRead").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canDelete").value(resource.isCanCreate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.canUpdate").value(resource.isCanCreate()));
    }

    /**
     * Test get by id ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Get By ID test")
    void testGetByIdOK() throws Exception {
        UUID restAclId = this.addAttribute();
        UUID roleId = this.addRole();
        RoleRestAclResource resource = RoleRestAclResource.builder().restAclId(restAclId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL.replace("{roleId}", roleId.toString()), resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restAclId").value(resource.getRestAclId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        RoleRestAclResource resource1 = TestUtils.convertJsonStringToObject(string, RoleRestAclResource.class);
        this.mockMvc.perform(this.get(BASE_URL.replace("{roleId}", roleId.toString()) + "/" + resource1.getRestAclId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restAclId").value(resource.getRestAclId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()));

        this.mockMvc.perform(this.get(BASE_URL.replace("{roleId}", roleId.toString()) + "/" + "Not Exist"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(
                        this.get(BASE_URL.replace("{roleId}", roleId.toString()) + "/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        string = this.mockMvc.perform(this.get(BASE_URL.replace("{roleId}", roleId.toString()))).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        HashMap<String, Object> roleResourcePage =
                TestUtils.convertJsonStringToObject(string, new TypeReference<HashMap<String, Object>>() {
                });
        assertTrue(((List<RoleRestAclResource>) roleResourcePage.get("items")).size() > 0);
    }

    /**
     * Test create new resource 400.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create User Role test Bad request")
    void testCreateNewResource400() throws Exception {
        RoleRestAclResource resource = Instancio.create(RoleRestAclResource.class);
        resource.setRoleId(null);
        resource.setRestAclId(null);
        this.mockMvc.perform(this.post(BASE_URL.replace("{roleId}", "notfound"), resource))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Test update user role ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test update user role")
    void testUpdateUserRoleOk() throws Exception {
        UUID restAclId = this.addAttribute();
        UUID roleId = this.addRole();
        RoleRestAclResource resource = RoleRestAclResource.builder().restAclId(restAclId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL.replace("{roleId}", roleId.toString()), resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restAclId").value(resource.getRestAclId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        RoleRestAclResource resource1 = TestUtils.convertJsonStringToObject(string, RoleRestAclResource.class);

        this.mockMvc.perform(this.put(BASE_URL.replace("{roleId}", roleId.toString()), resource1)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restAclId").value(resource.getRestAclId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()));

    }

    /**
     * Test delete user role by id ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Delete role attribute test")
    void testDeleteUserRoleByIdOk() throws Exception {
        UUID restAclId = this.addAttribute();
        UUID roleId = this.addRole();
        RoleRestAclResource resource = RoleRestAclResource.builder().restAclId(restAclId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL.replace("{roleId}", roleId.toString()), resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restAclId").value(resource.getRestAclId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        RoleRestAclResource resource1 = TestUtils.convertJsonStringToObject(string, RoleRestAclResource.class);
        this.mockMvc.perform(this.delete(BASE_URL.replace("{roleId}", roleId.toString()) + "/" + resource1.getRestAclId()))
                .andExpect(status().isNoContent());


        this.mockMvc.perform(this.get(BASE_URL.replace("{roleId}", roleId.toString()) + resource1.getRestAclId()))
                .andExpect(status().isNotFound());
    }
}
