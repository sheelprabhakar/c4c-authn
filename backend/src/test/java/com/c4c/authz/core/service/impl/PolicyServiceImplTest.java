package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.core.entity.RestAclEntity;
import com.c4c.authz.core.entity.RoleRestAclEntity;
import com.c4c.authz.core.service.api.RoleRestAclService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * The type Policy service impl test.
 */
class PolicyServiceImplTest {

  /**
   * The Role rest acl service.
   */
  @Mock
  private RoleRestAclService roleRestAclService;

  /**
   * The Policy service.
   */
  @InjectMocks
  private PolicyServiceImpl policyService;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Gets policies by role id.
   */
  @Test
  @DisplayName("Test getPoliciesByRoleId OK")
  void getPoliciesByRoleId() {
    UUID roleId = UUID.randomUUID();
    RoleRestAclEntity roleRestAclEntity = new RoleRestAclEntity();
    RestAclEntity restAclEntity = new RestAclEntity();
    restAclEntity.setName("Test Attribute");
    restAclEntity.setPath("/test/path");
    roleRestAclEntity.setRestAclEntity(restAclEntity);
    roleRestAclEntity.setCanCreate(true);
    roleRestAclEntity.setCanRead(true);

    when(roleRestAclService.findAllByRoleId(roleId)).thenReturn(Arrays.asList(roleRestAclEntity));

    List<PolicyRecord> policies = policyService.getPoliciesByRoleId(roleId);

    assertNotNull(policies);
    assertEquals(1, policies.size());
    PolicyRecord policyRecord = policies.get(0);
    assertEquals("Test Attribute", policyRecord.name());
    assertEquals("/test/path", policyRecord.path());
    assertTrue(policyRecord.verbs().contains("POST"));
    assertTrue(policyRecord.verbs().contains("GET"));

    when(roleRestAclService.findAllByRoleId(roleId)).thenReturn(Collections.EMPTY_LIST);

    policies = policyService.getPoliciesByRoleId(roleId);

    assertNotNull(policies);
    assertEquals(0, policies.size());
  }
}