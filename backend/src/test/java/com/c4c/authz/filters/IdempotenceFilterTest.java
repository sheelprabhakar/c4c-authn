package com.c4c.authz.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * The type Idempotence filter test.
 */
public class IdempotenceFilterTest {

  /**
   * The constant REQUEST_HEADER_RID.
   */
  public static final String REQUEST_HEADER_RID = "rid";
  /**
   * The constant REQUEST_HEADER_SID.
   */
  public static final String REQUEST_HEADER_SID = "sid";
  /**
   * The constant GIVEN_CHARSET.
   */
  private static final String GIVEN_CHARSET = "UTF-8";

  /**
   * The constant GIVEN_URI.
   */
  private static final String GIVEN_URI = "/given/uri/path";
  /**
   * The constant GIVEN_RID.
   */
  private static final String GIVEN_RID = "givenRid";
  /**
   * The constant GIVEN_SID.
   */
  private static final String GIVEN_SID = "givenSid";
  /**
   * The Redis template.
   */
  private RedisTemplate<String, IdempotenceFilter.IdempotencyValue> redisTemplate = mock(RedisTemplate.class);

  /**
   * The Sut.
   */
  private IdempotenceFilter sut = new IdempotenceFilter(redisTemplate, 60);

  /**
   * The Mock request.
   */
  private MockHttpServletRequest mockRequest;
  /**
   * The Mock response.
   */
  private MockHttpServletResponse mockResponse;
  /**
   * The Mock filter chain.
   */
  private FilterChain mockFilterChain;

  /**
   * Sets filter chain.
   */
  @BeforeEach
    void setupFilterChain() {
        this.mockFilterChain = mock(FilterChain.class);
    }

  /**
   * Given post valid header no cache exist response 200 when do filter internal then should cache response.
   *
   * @throws ServletException the servlet exception
   * @throws IOException      the io exception
   */
  @Test
    void givenPost_ValidHeader_NoCacheExist_Response200_whenDoFilterInternal_thenShouldCacheResponse() throws ServletException, IOException {
        String givenMethod = "POST";
        int givenStatus = 200;
        String givenResponseBody = "{'response-key': 'response-value'}";
        String expectedCacheKey = "POST_/given/uri/path_givenSid_givenRid";
        Integer expectedStatus = 200;

        setupRequest(givenMethod, GIVEN_URI, GIVEN_RID, GIVEN_SID);
        setupResponse(givenStatus, givenResponseBody);

        BoundValueOperations<String, IdempotenceFilter.IdempotencyValue> mockBoundValueOps =
                mock(BoundValueOperations.class);
        given(redisTemplate.boundValueOps(any())).willReturn(mockBoundValueOps);
        given(mockBoundValueOps.setIfAbsent(any(), anyLong(), any())).willReturn(Boolean.TRUE);

        sut.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        then(mockFilterChain).should().doFilter(eq(mockRequest), any());
        then(redisTemplate).should().boundValueOps(expectedCacheKey);
        then(mockBoundValueOps).should().setIfAbsent(assertArg(value -> {
            assertFalse(value.isDone());
        }), anyLong(), any());
        then(mockBoundValueOps).should().set(assertArg(value -> {
            assertEquals(expectedStatus, value.status());
            assertTrue(value.isDone());
        }), anyLong(), any());
    }

  /**
   * Sets response.
   *
   * @param status       the status
   * @param responseBody the response body
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  private void setupResponse(int status, String responseBody) throws UnsupportedEncodingException {
        this.mockResponse = new MockHttpServletResponse();
        mockResponse.setStatus(status);
        mockResponse.getWriter().write(responseBody);
    }

  /**
   * Sets request.
   *
   * @param givenMethod the given method
   * @param givenUri    the given uri
   * @param givenRid    the given rid
   * @param givenSid    the given sid
   */
  private void setupRequest(String givenMethod, String givenUri, String givenRid, String givenSid) {
        this.mockRequest = new MockHttpServletRequest(givenMethod, givenUri);
        this.mockRequest.setContentType(APPLICATION_JSON_VALUE);
        this.mockRequest.setCharacterEncoding(GIVEN_CHARSET);
        if (givenRid != null)
            this.mockRequest.addHeader(REQUEST_HEADER_RID, givenRid);
        if (givenSid != null)
            this.mockRequest.addHeader(REQUEST_HEADER_SID, givenSid);
    }

  /**
   * Given post valid header no cache exist response 404 when do filter internal then should delete cache.
   *
   * @throws ServletException the servlet exception
   * @throws IOException      the io exception
   */
  @Test
    void givenPost_ValidHeader_NoCacheExist_Response404_whenDoFilterInternal_thenShouldDeleteCache() throws ServletException, IOException {
        String givenMethod = "POST";
        int givenStatus = 404;
        String givenResponseBody = "{'response-key': 'response-value'}";
        String expectedCacheKey = "POST_/given/uri/path_givenSid_givenRid";

        setupRequest(givenMethod, GIVEN_URI, GIVEN_RID, GIVEN_SID);
        setupResponse(givenStatus, givenResponseBody);

        BoundValueOperations<String, IdempotenceFilter.IdempotencyValue> mockBoundValueOps =
                mock(BoundValueOperations.class);
        given(redisTemplate.boundValueOps(any())).willReturn(mockBoundValueOps);
        given(mockBoundValueOps.setIfAbsent(any(), anyLong(), any())).willReturn(Boolean.TRUE);
        given(mockBoundValueOps.getKey()).willReturn(expectedCacheKey);

        sut.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        then(mockFilterChain).should().doFilter(eq(mockRequest), any());
        then(redisTemplate).should().boundValueOps(expectedCacheKey);
        then(mockBoundValueOps).should().setIfAbsent(assertArg(value -> {
            assertFalse(value.isDone());
        }), anyLong(), any());
        then(redisTemplate).should().delete(expectedCacheKey);
    }

  /**
   * Given post valid header cache exist is processing when do filter internal then should return 425 error.
   *
   * @throws ServletException the servlet exception
   * @throws IOException      the io exception
   */
  @Test
    void givenPost_ValidHeader_CacheExistIsProcessing_whenDoFilterInternal_thenShouldReturn425Error() throws ServletException, IOException {
        String givenMethod = "POST";
        setupRequest(givenMethod, GIVEN_URI, GIVEN_RID, GIVEN_SID);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        String expectedCacheKey = "POST_/given/uri/path_givenSid_givenRid";
        Integer expectedStatus = 425;

        BoundValueOperations<String, IdempotenceFilter.IdempotencyValue> mockBoundValueOps =
                mock(BoundValueOperations.class);
        given(redisTemplate.boundValueOps(any())).willReturn(mockBoundValueOps);
        given(httpServletResponse.getWriter()).willReturn(mock(PrintWriter.class));
        given(mockBoundValueOps.setIfAbsent(any(), anyLong(), any())).willReturn(Boolean.FALSE);
        given(mockBoundValueOps.get()).willReturn(mockInprogressIdempotencyValue());

        sut.doFilterInternal(mockRequest, httpServletResponse, mockFilterChain);

        then(mockFilterChain).should(never()).doFilter(any(), any());
        then(redisTemplate).should().boundValueOps(expectedCacheKey);
        then(mockBoundValueOps).should(never()).set(any(), anyLong(), any());
        then(httpServletResponse).should().setStatus(expectedStatus);
        then(httpServletResponse).should().flushBuffer();
    }

  /**
   * Mock inprogress idempotency value idempotence filter . idempotency value.
   *
   * @return the idempotence filter . idempotency value
   */
  private IdempotenceFilter.IdempotencyValue mockInprogressIdempotencyValue() {
        return IdempotenceFilter.IdempotencyValue.init();
    }

  /**
   * Given post valid header cache exist is done when do filter internal then should return cached response.
   *
   * @throws ServletException the servlet exception
   * @throws IOException      the io exception
   */
  @Test
    void givenPost_ValidHeader_CacheExistIsDone_whenDoFilterInternal_thenShouldReturnCachedResponse() throws ServletException, IOException {
        String givenMethod = "POST";
        String givenCacheValue = "{'response-key': 'response-value'}";
        String expectedCacheKey = "POST_/given/uri/path_givenSid_givenRid";
        Integer expectedStatus = 200;

        setupRequest(givenMethod, GIVEN_URI, GIVEN_RID, GIVEN_SID);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        BoundValueOperations<String, IdempotenceFilter.IdempotencyValue> mockBoundValueOps =
                mock(BoundValueOperations.class);
        PrintWriter mockPrintWriter = mock(PrintWriter.class);
        given(redisTemplate.boundValueOps(any())).willReturn(mockBoundValueOps);
        given(httpServletResponse.getWriter()).willReturn(mockPrintWriter);
        given(mockBoundValueOps.setIfAbsent(any(), anyLong(), any())).willReturn(Boolean.FALSE);
        given(mockBoundValueOps.get()).willReturn(mockDoneIdempotencyValue(givenCacheValue));

        sut.doFilterInternal(mockRequest, httpServletResponse, mockFilterChain);

        then(mockFilterChain).should(never()).doFilter(any(), any());
        then(redisTemplate).should().boundValueOps(expectedCacheKey);
        then(mockBoundValueOps).should(never()).set(any(), anyLong(), any());
        then(httpServletResponse).should().setStatus(expectedStatus);
        then(mockPrintWriter).should().write(givenCacheValue);
        then(httpServletResponse).should().flushBuffer();
    }

  /**
   * Mock done idempotency value idempotence filter . idempotency value.
   *
   * @param cacheValue the cache value
   * @return the idempotence filter . idempotency value
   */
  private IdempotenceFilter.IdempotencyValue mockDoneIdempotencyValue(String cacheValue) {
        return IdempotenceFilter.IdempotencyValue.done(Collections.emptyMap(), 200, cacheValue);
    }

  /**
   * Given get method when do filter internal then should do filter directly.
   *
   * @throws ServletException the servlet exception
   * @throws IOException      the io exception
   */
  @Test
    void givenGetMethod_whenDoFilterInternal_thenShouldDoFilterDirectly() throws ServletException, IOException {
        String givenMethod = "GET";
        int givenStatus = 200;
        String givenResponseBody = "{'response-key': 'response-value'}";

        setupRequest(givenMethod, GIVEN_URI, GIVEN_RID, GIVEN_SID);
        setupResponse(givenStatus, givenResponseBody);

        sut.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        then(mockFilterChain).should().doFilter(mockRequest, mockResponse);
        then(redisTemplate).shouldHaveNoInteractions();
    }

  /**
   * Rid sid arguments stream.
   *
   * @return the stream
   */
  private static Stream<Arguments> ridSidArguments() {
        return Stream.of(
                // Arguments.of(rid, sid)
                Arguments.of(null, null), // both null
                Arguments.of(" ", " "), // both blank
                Arguments.of(" ", "sid"), // rid is blank
                Arguments.of("rid", "") // sid is blank

        );
    }

  /**
   * Given post without rid or sid when do filter internal then should do filter directly.
   *
   * @param givenRid the given rid
   * @param givenSid the given sid
   * @throws ServletException the servlet exception
   * @throws IOException      the io exception
   */
  @ParameterizedTest
    @MethodSource("ridSidArguments")
    void givenPost_WithoutRidOrSid_whenDoFilterInternal_thenShouldDoFilterDirectly(String givenRid, String givenSid) throws ServletException, IOException {
        String givenMethod = "POST";
        setupRequest(givenMethod, GIVEN_URI, givenRid, givenSid);
        setupResponse(200, "{'response-key': 'response-value'}");

        sut.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        then(mockFilterChain).should().doFilter(mockRequest, mockResponse);
        then(redisTemplate).shouldHaveNoInteractions();
    }

}