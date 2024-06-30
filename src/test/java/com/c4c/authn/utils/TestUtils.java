package com.c4c.authn.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * The type Test utils.
 */
public class TestUtils {
  /**
   * The constant mapper_.
   */
  private static final ObjectMapper mapper_ = new ObjectMapper();

  /**
   * Convert json string to object t.
   *
   * @param <T>       the type parameter
   * @param jsonStr   the json str
   * @param classType the class type
   * @return the t
   * @throws IOException the io exception
   */
  public static <T> T convertJsonStringToObject(final String jsonStr,
                                                final Class<T> classType) throws IOException {
    return mapper_.readValue(jsonStr, classType);
  }

  /**
   * Convert object to json stream byte [ ].
   *
   * @param object the object
   * @return the byte [ ]
   * @throws JsonProcessingException the json processing exception
   */
  public static byte[] convertObjectToJsonStream(final Object object)
      throws JsonProcessingException {
    return mapper_.writeValueAsBytes(object);
  }

  /**
   * Convert object to json string string.
   *
   * @param object the object
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  public static String convertObjectToJsonString(final Object object)
      throws JsonProcessingException {
    return mapper_.writeValueAsString(object);
  }

  /**
   * Gets mapper.
   *
   * @return the mapper
   */
  public static ObjectMapper getMapper() {
    return mapper_;
  }


  public static String generateRandString(int length){
    byte[] array = new byte[length]; // length is bounded by 7
    new Random().nextBytes(array);
    return new String(array, Charset.forName("UTF-8"));
  }
}
