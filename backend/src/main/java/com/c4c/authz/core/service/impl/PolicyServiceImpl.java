package com.c4c.authz.core.service.impl;

import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.entity.ClientRoleEntity;
import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.entity.UserRoleEntity;
import com.c4c.authz.core.service.api.ClientRoleService;
import com.c4c.authz.core.service.api.ClientService;
import com.c4c.authz.core.service.api.PolicyService;
import com.c4c.authz.core.service.api.RoleAttributeService;
import com.c4c.authz.core.service.api.UserRoleService;
import com.c4c.authz.core.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.c4c.authz.common.Constants.ITEM_CACHE;

/**
 * The type Policy service.
 */
@Service
@Slf4j
public class PolicyServiceImpl implements PolicyService {
    /**
     * The Role attribute service.
     */
    private final RoleAttributeService roleAttributeService;

    /**
     * The Client service.
     */
    private final ClientService clientService;

    /**
     * The User service.
     */
    private final UserService userService;
    /**
     * The Client role service.
     */
    private final ClientRoleService clientRoleService;

    /**
     * The User role service.
     */
    private final UserRoleService userRoleService;

    /**
     * Instantiates a new Policy service.
     *
     * @param roleAttributeService the role attribute service
     * @param clientService        the client service
     * @param userService          the user service
     * @param clientRoleService    the client role service
     * @param userRoleService      the user role service
     */
    public PolicyServiceImpl(final RoleAttributeService roleAttributeService, final ClientService clientService,
                             final UserService userService,
                             final ClientRoleService clientRoleService, final UserRoleService userRoleService) {
        this.roleAttributeService = roleAttributeService;
        this.clientService = clientService;
        this.userService = userService;
        this.clientRoleService = clientRoleService;
        this.userRoleService = userRoleService;
    }

    /**
     * Gets policies by role id.
     *
     * @param roleId the role id
     * @return the policies by role id
     */
    @Cacheable(cacheNames = ITEM_CACHE, key = "#p0")
    @Override
    public List<PolicyRecord> getPoliciesByRoleId(final UUID roleId) {
        List<RoleAttributeEntity> allByRoleId = this.roleAttributeService.findAllByRoleId(roleId);
        List<PolicyRecord> policyRecords = new ArrayList<>();
        allByRoleId.forEach(entity -> {
            PolicyRecord policyRecord = new PolicyRecord(entity.getAttributeEntity().getName(),
                    entity.getAttributeEntity().getPath(), getVerbs(entity));
            policyRecords.add(policyRecord);
        });

        return policyRecords;
    }

    /**
     * Gets policies for current client.
     *
     * @param tenantId the tenant id
     * @param clientId the client id
     * @return the policies for current client
     */
    @Override
    @Cacheable(cacheNames = ITEM_CACHE, key = "#p0")
    public List<PolicyRecord> getPoliciesForCurrentClient(final UUID tenantId, final String clientId) {
        List<PolicyRecord> policyRecords = new ArrayList<>();
        ClientEntity clientEntity = this.clientService.findByTenantIdAndClientId(tenantId, clientId);
        List<ClientRoleEntity> clientRoleEntities = this.clientRoleService.findByClientId(clientEntity.getId());
        for (ClientRoleEntity cr : clientRoleEntities) {
            policyRecords.addAll(this.getPoliciesByRoleId(cr.getRoleId()));
        }

        if (policyRecords.isEmpty()) {
            log.error("Client or role not found for tenantId: {} and clientId: {}", tenantId, clientId);
        }
        return policyRecords;
    }

    /**
     * Gets policies for current user.
     *
     * @param tenantId    the tenant id
     * @param currentUser the current user
     * @return the policies for current user
     */
    @Override
    @Cacheable(cacheNames = ITEM_CACHE, key = "#p0")
    public List<PolicyRecord> getPoliciesForCurrentUser(final UUID tenantId, final String currentUser) {
        List<PolicyRecord> policyRecords = new ArrayList<>();
        UserEntity userEntity = this.userService.findByUserName(currentUser);
        List<UserRoleEntity> userRoleEntities = this.userRoleService.findByUserId(userEntity.getId());
        for (UserRoleEntity ur : userRoleEntities) {
            policyRecords.addAll(this.getPoliciesByRoleId(ur.getRoleId()));
        }

        if (policyRecords.isEmpty()) {
            log.error("Client or role not found for tenantId: {} and clientId: {}", tenantId, currentUser);
        }
        return policyRecords;
    }

    /**
     * Gets verbs.
     *
     * @param entity the entity
     * @return the verbs
     */
    private static List<String> getVerbs(final RoleAttributeEntity entity) {
        List<String> verbs = new ArrayList<>();
        if (entity.isCanCreate()) {
            verbs.add(HttpMethod.POST.name());
        }
        if (entity.isCanDelete()) {
            verbs.add(HttpMethod.DELETE.name());
        }
        if (entity.isCanUpdate()) {
            verbs.add(HttpMethod.PUT.name());
            verbs.add(HttpMethod.PATCH.name());
        }
        if (entity.isCanRead()) {
            verbs.add(HttpMethod.GET.name());
        }
        return verbs;
    }
}
