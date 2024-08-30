package com.c4c.authz.core.domain;

import java.util.List;

/**
 * The type Policy record.
 */
public record PolicyRecord(String name, String path, List<String> verbs) implements Comparable<PolicyRecord> {
  /**
   * Equals boolean.
   *
   * @param o the o
   * @return the boolean
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PolicyRecord that = (PolicyRecord) o;
    return name.equals(that.name) && path.equals(that.path);
  }

  /**
   * Hash code int.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + path.hashCode();
    return result;
  }

  /**
   * Compare to int.
   *
   * @param o the o
   * @return the int
   */
  @Override
  public int compareTo(final PolicyRecord o) {
    return this.name.compareTo(o.name);
  }
}
