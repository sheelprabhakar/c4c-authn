package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.TENANT_URL;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.rest.resource.TenantResource;
import com.c4c.authz.utils.TenantResourceHelper;
import com.c4c.authz.utils.TestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * The type Tenant controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TenantControllerTest extends AbstractIntegrationTest {
    /**
     * The constant BASE_URL.
     */
    private static final String BASE_URL = API_V1 + TENANT_URL;

    /**
     * Test create tenant tenant ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create new tenant OK")
    void test_create_Tenant_tenant_ok() throws Exception {
        TenantResource resource = TenantResourceHelper.getNew();
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));
    }

    /**
     * Test create tenant tenant duplicate.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Create duplicate tenant bad Request")
    void test_create_Tenant_tenant_duplicate() throws Exception {
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
     * Test create tenant tenant validation.
     *
     * @param resource the resource
     * @throws Exception the exception
     */
    @ParameterizedTest
    @MethodSource("getTestTenantResource")
    @DisplayName("Create tenant check for validation")
    void test_create_Tenant_tenant_validation(TenantResource resource) throws Exception {
        this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Gets test tenant resource.
     *
     * @return the test tenant resource
     */
    static Stream<TenantResource> getTestTenantResource() {
        List<TenantResource> resourceList = new ArrayList<>();
        TenantResource tenantResource = new TenantResource();
        resourceList.add(tenantResource);

        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setName(null);
        resourceList.add(tenantResource);

        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setName("");
        resourceList.add(tenantResource);


        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setName("123456789123456789012345678912345678901234567891234567890");
        resourceList.add(tenantResource);

        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setEmail("");
        resourceList.add(tenantResource);

        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setEmail(null);
        resourceList.add(tenantResource);

        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setEmail("dafsafggfgdsgdfg-.com");
        resourceList.add(tenantResource);

        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setAddress("");
        resourceList.add(tenantResource);

        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setAddress(null);
        resourceList.add(tenantResource);

        tenantResource = TenantResourceHelper.getNew();
        tenantResource.setAddress(RandomStringUtils.random(300, true, true));
        resourceList.add(tenantResource);

        List<TenantResource> resourceList1 = Instancio.ofList(TenantResource.class)
                .size(20)
                .generate(field(TenantResource::getEmail), gen -> gen.text().pattern("#a#a#a#a#a#a@example.com"))
                .create();
        resourceList.addAll(resourceList1);

        return resourceList.stream();
    }

    /**
     * Tenant update ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test tenant update OK")
    void tenantUpdateOK() throws Exception {
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
     * Tenant read ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test Tenant Read operation")
    void tenantReadOk() throws Exception {
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

        result = this.mockMvc.perform(this.get(BASE_URL+"?pageSize=10&pageIndex=0"))
                //.andDo(print())
                .  andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PagedModelResponse<TenantResource>
                resourceList = TestUtils.convertJsonStringToObject(result, PagedModelResponse.class);
        assertTrue(resourceList.getItems().size() > 0);

        result = this.mockMvc.perform(this.get(BASE_URL))
                //.andDo(print())
                .  andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
                resourceList = TestUtils.convertJsonStringToObject(result, PagedModelResponse.class);
        assertTrue(resourceList.getItems().size() > 0);


    }

    /**
     * Tenant get no found.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test Tenant read operation for non existing tenant UUID")
    void tenantGetNoFound() throws Exception {
        this.mockMvc.perform(this.get(BASE_URL + "/" + UUID.randomUUID().toString()))
                //.andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Tenant delete by id ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test Tenant delete operation")
    void tenantDeleteByIdOk() throws Exception {
        TenantResource resource = TenantResourceHelper.getNew();
        String result = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TenantResource tenantResource = TestUtils.convertJsonStringToObject(result, TenantResource.class);
        this.mockMvc.perform(this.delete(BASE_URL + "/" + tenantResource.getId()))
                //.andDo(print())
                .andExpect(status().isNoContent());

        this.mockMvc.perform(this.delete(BASE_URL + "/" + UUID.randomUUID().toString()))
                //.andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Tenant delete by id no found.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test Tenant delete operation for non existing tenant UUID")
    void tenantDeleteByIdNoFound() throws Exception {
        this.mockMvc.perform(this.delete(BASE_URL + "/" + UUID.randomUUID().toString()))
                //.andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Role read ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test Tenant Read operation")
    void roleReadOk() throws Exception {
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
                .  andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PagedModelResponse<TenantResource>
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
