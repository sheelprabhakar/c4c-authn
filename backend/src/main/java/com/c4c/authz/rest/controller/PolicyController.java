package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.rest.resource.PolicyResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.POLICY_URL;

/**
 * The type Policy controller.
 */
@Slf4j
@RestController
@RequestMapping(PolicyController.BASE_URL)
public class PolicyController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = API_V1 + POLICY_URL;

    /**
     * Instantiates a new Policy controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    public PolicyController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Gets policies for current user.
     *
     * @param tenantId the tenant id
     * @return the policies for current user
     */
    @GetMapping("/me")
    public ResponseEntity<List<PolicyResource>> getPoliciesForCurrentUser(
            @PathVariable(value = "tenantId") final UUID tenantId) {
        if (tenantId == null || !tenantId.equals(CurrentUserContext.getCurrentTenantId())) {
            log.error("Tenant ID does not match the current tenant ID");
            return ResponseEntity.badRequest().build();
        }
        List<PolicyResource> policies = this.getRestAdapterV1().getPoliciesForCurrentUser();
        return ResponseEntity.ok(policies);
    }

    /**
     * Gets policies for current client.
     *
     * @param tenantId the tenant id
     * @return the policies for current client
     */
    @GetMapping("/client")
    public ResponseEntity<List<PolicyResource>> getPoliciesForCurrentClient(
            @PathVariable(value = "tenantId") final UUID tenantId) {
        if (tenantId == null || !tenantId.equals(CurrentUserContext.getCurrentTenantId())) {
            log.error("Tenant ID does not match the current tenant ID");
            return ResponseEntity.badRequest().build();
        }
        List<PolicyResource> policies = this.getRestAdapterV1().getPoliciesForCurrentClient();
        return ResponseEntity.ok(policies);
    }
}
