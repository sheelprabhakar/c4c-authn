package com.c4c.authn.filters;

import com.c4c.authn.common.CurrentUserContext;
import com.c4c.authn.common.exception.CustomException;
import com.c4c.authn.core.service.impl.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.c4c.authn.common.Constants.AUTH_URL;
import static com.c4c.authn.common.Constants.LOOKUP_URL;

/**
 * The type Jwt token filter.
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    /**
     * The Jwt token provider.
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Instantiates a new Jwt token filter.
     *
     * @param jwtTokenProvider the jwt token provider
     */
    public JwtTokenFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Do filter internal.
     *
     * @param httpServletRequest  the http servlet request
     * @param httpServletResponse the http servlet response
     * @param filterChain         the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    final HttpServletResponse httpServletResponse,
                                    final FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        //Check Tenant header
        if (!this.updateUserContext(httpServletRequest, httpServletResponse)) {
            return;
        }
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                CurrentUserContext.setCurrentUser(auth.getName());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomException ex) {
            log.info("Toke authentication failed");
            // this is very important, since it guarantees the user is not authenticated at
            // all
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * Update user context boolean.
     *
     * @param httpServletRequest  the http servlet request
     * @param httpServletResponse the http servlet response
     * @return the boolean
     * @throws IOException the io exception
     */
    private boolean updateUserContext(final HttpServletRequest httpServletRequest,
                                      final HttpServletResponse httpServletResponse) throws IOException {
        log.info("In preHandle we are Intercepting the Request");
        String requestURI = httpServletRequest.getRequestURI();
        if (!(requestURI.contains(AUTH_URL) || requestURI.contains(LOOKUP_URL))) {
            String tenantID = httpServletRequest.getHeader("X-TenantID");
            if (tenantID == null) {
                httpServletResponse.getWriter().write("X-TenantID not present in the Request Header");
                httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                return false;
            } else {
                CurrentUserContext.setCurrentTenant(tenantID);
            }
        }
        return true;
    }
}


