package com.c4c.authn.common;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * The type Spring util.
 */
public final class SpringUtil {
    /**
     * Instantiates a new Spring util.
     */
    private SpringUtil() {

  }

    /**
     * Is super admin boolean.
     *
     * @return the boolean
     */
    public static boolean isSuperAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
        .anyMatch(x -> x.getAuthority().equals(Constants.SUPER_ADMIN));
  }

    /**
     * Is tenant admin boolean.
     *
     * @return the boolean
     */
    public static boolean isTenantAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
        .anyMatch(x -> x.getAuthority().equals(Constants.TENANT_ADMIN));
  }

    /**
     * Gets loggedin user.
     *
     * @return the loggedin user
     */
    public static String getLoggedinUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (String) authentication.getPrincipal();
  }

    /**
     * Gets tenant id.
     *
     * @return the tenant id
     */
    public static UUID getTenantId() {
    RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
    if (attribs != null) {
      HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
      if (Objects.nonNull(request.getHeader("X-TENANT-ID"))) {
        return UUID.fromString(request.getHeader("X-TENANT-ID"));
      }
    }
    return null;
  }
}
