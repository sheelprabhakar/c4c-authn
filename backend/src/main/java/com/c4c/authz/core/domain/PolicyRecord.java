package com.c4c.authz.core.domain;

import java.util.List;

/**
 * The type Attribute record.
 */
public record PolicyRecord(String name, String path, List<String> verbs) implements Comparable<PolicyRecord> {
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

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + path.hashCode();
    return result;
  }

  @Override
  public int compareTo(final PolicyRecord o) {
    return this.name.compareTo(o.name);
  }
}
