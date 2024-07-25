package com.c4c.authn.config.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.c4c.authn.common.Constants.AUTH_URL;
import static com.c4c.authn.common.Constants.LOOKUP_URL;
import static com.c4c.authn.common.Constants.TENANT_URL;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        log.info("In preHandle we are Intercepting the Request");
        String requestURI = request.getRequestURI();
        if (requestURI.contains(AUTH_URL) || requestURI.contains(TENANT_URL) || requestURI.contains(LOOKUP_URL)) {
            return true;
        }
        String tenantID = request.getHeader("X-TenantID");
        if (tenantID == null) {
            response.getWriter().write("X-TenantID not present in the Request Header");
            response.setStatus(400);
            return false;
        }
        CurrentUserContext.setCurrentTenant(tenantID);
        return true;
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        CurrentUserContext.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}