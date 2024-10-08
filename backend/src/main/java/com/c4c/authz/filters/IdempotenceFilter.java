package com.c4c.authz.filters;

import static java.lang.String.join;
import static org.springframework.http.HttpStatus.TOO_EARLY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * The type Idempotence filter.
 */
@Slf4j
@RequiredArgsConstructor
public class IdempotenceFilter extends OncePerRequestFilter {
    /**
     * The constant REQUEST_ID_KEY.
     */
    private static final String REQUEST_ID_KEY = "rid";

    /**
     * The constant SERVICE_ID_KEY.
     */
    private static final String SERVICE_ID_KEY = "sid";

    /**
     * The constant DELIMITER.
     */
    public static final String DELIMITER = "_";

    /**
     * The Redis template.
     */
    private final RedisTemplate<String, IdempotencyValue> redisTemplate;

    /**
     * The Ttl.
     */
    private final long ttl;

    /**
     * The constant OBJECT_MAPPER.
     */
    private static final ObjectMapper OBJECT_MAPPER = initObjectMapper();

    /**
     * Init object mapper object mapper.
     *
     * @return the object mapper
     */
    private static ObjectMapper initObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    /**
     * Do filter internal.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        log.debug("start IdempotenceFilter");

        String method = request.getMethod();
        String requestId = request.getHeader(REQUEST_ID_KEY);
        String serviceId = request.getHeader(SERVICE_ID_KEY);
        String cacheKey = join(DELIMITER, method, request.getRequestURI(), serviceId, requestId);

        if (this.isNotTargetMethod(method)) {
            log.info("Request method {} didn't match the target idempotency https method.", method);
            filterChain.doFilter(request, response);
        } else if (StringUtils.isBlank(requestId) || StringUtils.isBlank(serviceId)) {
            log.warn("Request should bring a RequestId and ServiceId in header, but no. get cacheKey as {}.", cacheKey);
            filterChain.doFilter(request, response);
        } else {
            log.info("requestId and serviceId not empty, rid = {}, sid = {}", requestId, serviceId);
            BoundValueOperations<String, IdempotencyValue> keyOperation = redisTemplate.boundValueOps(cacheKey);
            boolean isAbsent = keyOperation.setIfAbsent(IdempotencyValue.init(), ttl, TimeUnit.MINUTES);
            if (isAbsent) {
                log.info("cache {} not exist ", cacheKey);
                ContentCachingResponseWrapper responseCopier = new ContentCachingResponseWrapper(response);

                filterChain.doFilter(request, responseCopier);
                // After rest execution
                this.updateResultInCache(request, responseCopier, keyOperation);
                responseCopier.copyBodyToResponse();
            } else {
                log.info("cache {} already exist ", cacheKey);
                this.handleWhenCacheExist(request, response, keyOperation);
            }

        }

    }

    /**
     * Is not target method boolean.
     *
     * @param method the method
     * @return the boolean
     */
    private boolean isNotTargetMethod(final String method) {
        return !(HttpMethod.POST.matches(method) || HttpMethod.PATCH.matches(method) || HttpMethod.PUT.matches(method));
    }

    /**
     * Update result in cache.
     *
     * @param request        the request
     * @param responseCopier the response copier
     * @param keyOperation   the key operation
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    private void updateResultInCache(final HttpServletRequest request,
                                     final ContentCachingResponseWrapper responseCopier,
                                     final BoundValueOperations<String, IdempotencyValue> keyOperation)
            throws UnsupportedEncodingException {
        if (this.needCache(responseCopier)) {
            log.info("Process result need to be cached");
            String responseBody = new String(responseCopier.getContentAsByteArray(), request.getCharacterEncoding());
            IdempotencyValue result =
                    IdempotencyValue.done(Collections.emptyMap(), responseCopier.getStatus(), responseBody);

            log.info("save {} to redis", result);
            keyOperation.set(result, ttl, TimeUnit.MINUTES);
        } else {
            log.info("process result don't need to be cached");
            this.redisTemplate.delete(keyOperation.getKey());
        }
    }

    /**
     * Handle when cache exist.
     *
     * @param request      the request
     * @param response     the response
     * @param keyOperation the key operation
     * @throws IOException the io exception
     */
    private void handleWhenCacheExist(final HttpServletRequest request, final HttpServletResponse response,
                                      final BoundValueOperations<String, IdempotencyValue> keyOperation)
            throws IOException {
        IdempotencyValue cachedResponse = keyOperation.get();
        log.info("cached content = {} ", cachedResponse);
        String responseBody;
        Integer status;

        if (cachedResponse.isDone) {
            log.info("cache {} exist, and is done.");
            status = cachedResponse.status;
            responseBody = cachedResponse.cacheValue;
        } else {
            log.info("cache exist, and is still in processing, please retry later");
            status = TOO_EARLY.value();
            ProblemDetail pd = ProblemDetail.forStatus(TOO_EARLY);
            pd.setType(URI.create(request.getRequestURI()));
            pd.setDetail("request is now processing, please try again later");
            responseBody = OBJECT_MAPPER.writeValueAsString(pd);
        }
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseBody);

        response.flushBuffer();

    }

    /**
     * Need cache boolean.
     *
     * @param responseCopier the response copier
     * @return the boolean
     */
    private boolean needCache(final ContentCachingResponseWrapper responseCopier) {
        int statusCode = responseCopier.getStatus();
        return statusCode >= HttpStatus.OK.value() && statusCode < HttpStatus.MULTIPLE_CHOICES.value();
    }

    /**
     * The type Idempotency value.
     */
    public record IdempotencyValue(Map<String, Object> header, int status, String cacheValue, boolean isDone) {

        /**
         * Init idempotency value.
         *
         * @return the idempotency value
         */
        protected static IdempotencyValue init() {
            return new IdempotencyValue(Collections.emptyMap(), 0, "", false);
        }

        /**
         * Done idempotency value.
         *
         * @param header     the header
         * @param status     the status
         * @param cacheValue the cache value
         * @return the idempotency value
         */
        protected static IdempotencyValue done(final Map<String, Object> header, final Integer status,
                                               final String cacheValue) {
            return new IdempotencyValue(header, status, cacheValue, true);
        }
    }
}
