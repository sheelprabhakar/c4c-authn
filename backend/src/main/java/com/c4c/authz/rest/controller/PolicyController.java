package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.POLICY_URL;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.rest.resource.PolicyResource;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Client controller.
 */
@Slf4j
@RestController
@RequestMapping(PolicyController.BASE_URL)
public class PolicyController extends BaseController {
  /**
   * The constant BASE_URL.
   */
  static final String BASE_URL = API_V1 + POLICY_URL;

  @Autowired
  public PolicyController(final RestAdapterV1 restAdapterV1) {
    super(restAdapterV1);
  }

  @GetMapping("/me")
  public ResponseEntity<List<PolicyResource>> getPoliciesForCurrentClient(
      @PathVariable(value = "tenantId") final UUID tenantId) {
    if(tenantId == null || !tenantId.equals(CurrentUserContext.getCurrentTenantId())) {
      log.error("Tenant ID does not match the current tenant ID");
      return ResponseEntity.badRequest().build();
    }
    List<PolicyResource> policies = this.getRestAdapterV1().getPoliciesForCurrentClient();
    return ResponseEntity.ok(policies);
  }
}
