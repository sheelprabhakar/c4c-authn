package com.c4c.authn.config.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.c4c.authn.common.Constants.AUTH_URL;
import static com.c4c.authn.common.Constants.LOOKUP_URL;
import static com.c4c.authn.common.Constants.TENANT_URL;

/**
 * The type Request interceptor.
 */
@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    /**
     * Pre handle boolean.
     *
     * @param request  the request
     * @param response the response
     * @param object   the object
     * @return the boolean
     * @throws Exception the exception
     */
    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object object) throws Exception {
        log.info("In preHandle we are Intercepting the Request");
        String requestURI = request.getRequestURI();
        if (requestURI.contains(AUTH_URL) || requestURI.contains(TENANT_URL) || requestURI.contains(LOOKUP_URL)) {
            return true;
        }
        String tenantID = request.getHeader("X-TenantID");
        if (tenantID == null) {
            response.getWriter().write("X-TenantID not present in the Request Header");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return false;
        }
        CurrentUserContext.setCurrentTenant(tenantID);
        return true;
    }

    /**
     * Post handle.
     *
     * @param request      the request
     * @param response     the response
     * @param handler      the handler
     * @param modelAndView the model and view
     * @throws Exception the exception
     */
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * After completion.
     *
     * @param request  the request
     * @param response the response
     * @param handler  the handler
     * @param ex       the ex
     * @throws Exception the exception
     */
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex)
            throws Exception {
        CurrentUserContext.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
