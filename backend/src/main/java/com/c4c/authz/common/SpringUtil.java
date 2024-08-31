package com.c4c.authz.common;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

  /**
   * From single item list.
   *
   * @param <T> the type parameter
   * @param t   the t
   * @return the list
   */
  public static <T> List<T> fromSingleItem(final T t) {
        List<T> list = new ArrayList<>();
        if (null != t) {
            list.add(t);
        }
        return list;
    }

  /**
   * Paged from single item page.
   *
   * @param <T> the type parameter
   * @param t   the t
   * @return the page
   */
  public static <T> Page<T> pagedFromSingleItem(final T t) {
        return new PageImpl<>(SpringUtil.fromSingleItem(t));
    }
}
