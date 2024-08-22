package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.SpringUtil;
import java.util.UUID;

/**
 * The type Base controller.
 */
public abstract class BaseController {
    /**
     * The Rest adapter v 1.
     */
    private final RestAdapterV1 restAdapterV1;

    /**
     * Instantiates a new Base controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    protected BaseController(final RestAdapterV1 restAdapterV1) {
    this.restAdapterV1 = restAdapterV1;
  }

    /**
     * Is super admin boolean.
     *
     * @return the boolean
     */
    boolean isSuperAdmin() {
    return SpringUtil.isSuperAdmin();
  }

    /**
     * Is tenant admin boolean.
     *
     * @return the boolean
     */
    boolean isTenantAdmin() {
    return SpringUtil.isTenantAdmin();
  }

    /**
     * Gets tenant id.
     *
     * @return the tenant id
     */
    UUID getTenantId() {
    return SpringUtil.getTenantId();
  }

    /**
     * Gets rest adapter v 1.
     *
     * @return the rest adapter v 1
     */
    RestAdapterV1 getRestAdapterV1() {
    return restAdapterV1;
  }

}
