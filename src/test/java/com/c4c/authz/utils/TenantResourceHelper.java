package com.c4c.authz.utils;

import com.c4c.authz.rest.resource.TenantResource;

public final class TenantResourceHelper {
  public static final String MOBILE = "989898989";
  static int counter = 0;

  static String EMAIL = "tenant@gmail.com";

  private TenantResourceHelper() {

  }

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
