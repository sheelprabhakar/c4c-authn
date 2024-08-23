package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.core.entity.AttributeEntity;
import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.service.api.RoleAttributeService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

class PolicyServiceImplTest {

  @Mock
  private RoleAttributeService roleAttributeService;

  @InjectMocks
  private PolicyServiceImpl policyService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test getPoliciesByRoleId OK")
  void getPoliciesByRoleId() {
    UUID roleId = UUID.randomUUID();
    RoleAttributeEntity roleAttributeEntity = new RoleAttributeEntity();
    AttributeEntity attributeEntity = new AttributeEntity();
    attributeEntity.setName("Test Attribute");
    attributeEntity.setPath("/test/path");
    roleAttributeEntity.setAttributeEntity(attributeEntity);
    roleAttributeEntity.setCanCreate(true);
    roleAttributeEntity.setCanRead(true);

    when(roleAttributeService.findAllByRoleId(roleId)).thenReturn(Arrays.asList(roleAttributeEntity));

    List<PolicyRecord> policies = policyService.getPoliciesByRoleId(roleId);

    assertNotNull(policies);
    assertEquals(1, policies.size());
    PolicyRecord policyRecord = policies.get(0);
    assertEquals("Test Attribute", policyRecord.name());
    assertEquals("/test/path", policyRecord.path());
    assertTrue(policyRecord.verbs().contains("POST"));
    assertTrue(policyRecord.verbs().contains("GET"));

    when(roleAttributeService.findAllByRoleId(roleId)).thenReturn(Collections.EMPTY_LIST);

    policies = policyService.getPoliciesByRoleId(roleId);

    assertNotNull(policies);
    assertEquals(0, policies.size());
  }
}