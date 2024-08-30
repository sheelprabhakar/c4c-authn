package com.c4c.authz.utils;

import com.c4c.authz.rest.resource.TenantResource;

/**
 * The type Tenant resource helper.
 */
public final class TenantResourceHelper {
  /**
   * The constant MOBILE.
   */
  public static final String MOBILE = "989898989";
  /**
   * The Counter.
   */
  static int counter = 0;

  /**
   * The Email.
   */
  static String EMAIL = "tenant@gmail.com";

  /**
   * Instantiates a new Tenant resource helper.
   */
  private TenantResourceHelper() {

  }

  /**
   * Gets new.
   *
   * @return the new
   */
  public static TenantResource getNew() {
    int c = counter++;
    return TenantResource.builder().name("Tenant" + c)
        .shortName("shotrn" + c)
        .email(c + EMAIL)
        .phone(c + MOBILE)
        .mobile(c + MOBILE)
        .cityId(12)
        .address("address" + c)
        .build();
  }
}
