package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.ROLE_URL;
import static com.c4c.authz.common.Constants.USER_ROLE_URL;
import static com.c4c.authz.common.Constants.USER_URL;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.authz.rest.resource.RoleResource;
import com.c4c.authz.rest.resource.user.UserResource;
import com.c4c.authz.rest.resource.user.UserRoleResource;
import com.c4c.authz.utils.TestUtils;
import com.c4c.authz.utils.UserResourceHelper;
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
 * The type User role controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRoleControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String BASE_URL = API_V1 + USER_ROLE_URL;

    /**
     * Add user uuid.
     *
     * @return the uuid
     * @throws Exception the exception
     */
    private UUID addUser() throws Exception {
        UserResource userResource = UserResourceHelper.getNew(null);
        String content = this.mockMvc.perform(this.post(API_V1 + USER_URL, userResource))
                //.andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        userResource = TestUtils.convertJsonStringToObject(content, UserResource.class);
        return userResource.getId();
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
    @DisplayName("Create User  Role test")
    void testCreateNewUserRoleOK() throws Exception {
        UUID userId = this.addUser();
        UUID roleId = this.addRole();
        UserRoleResource resource = UserRoleResource.builder().userId(userId).roleId(roleId).build();
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(resource.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()));
    }

    /**
     * Test get by id ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Get By ID test")
    void testGetByIdOK() throws Exception {
        UUID userId = this.addUser();
        UUID roleId = this.addRole();
        UserRoleResource resource = UserRoleResource.builder().userId(userId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(resource.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        UserRoleResource resource1 = TestUtils.convertJsonStringToObject(string, UserRoleResource.class);
        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getUserId() + "/" + resource1.getRoleId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(resource.getUserId().toString()))
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
        assertTrue(((List<UserRoleResource>) roleResourcePage.get("items")).size() > 0);
    }

    /**
     * Test create new resource 400.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create User Role test Bad request")
    void testCreateNewResource400() throws Exception {
        UserRoleResource resource = Instancio.create(UserRoleResource.class);
        resource.setRoleId(null);
        resource.setUserId(null);
        this.mockMvc.perform(this.post(BASE_URL, resource))
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
        UUID userId = this.addUser();
        UUID roleId = this.addRole();
        UserRoleResource resource = UserRoleResource.builder().userId(userId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(resource.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        UserRoleResource resource1 = TestUtils.convertJsonStringToObject(string, UserRoleResource.class);

        this.mockMvc.perform(this.put(BASE_URL, resource1)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(resource.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()));

    }

    /**
     * Test delete user role by id ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Delete user role test")
    void testDeleteUserRoleByIdOk() throws Exception {
        UUID userId = this.addUser();
        UUID roleId = this.addRole();
        UserRoleResource resource = UserRoleResource.builder().userId(userId).roleId(roleId).build();
        MvcResult mvcResult = this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(resource.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roleId").value(resource.getRoleId().toString()))
                .andReturn();

        String string = mvcResult.getResponse().getContentAsString();
        UserRoleResource resource1 = TestUtils.convertJsonStringToObject(string, UserRoleResource.class);
        this.mockMvc.perform(this.delete(BASE_URL + "/" + resource1.getUserId() + "/" + resource1.getRoleId()))
                .andExpect(status().isNoContent());


        this.mockMvc.perform(this.get(BASE_URL + "/" + resource1.getUserId() + "/" + resource1.getRoleId()))
                .andExpect(status().isNotFound());
    }
}
