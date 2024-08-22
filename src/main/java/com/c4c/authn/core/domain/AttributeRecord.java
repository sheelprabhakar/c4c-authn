package com.c4c.authn.core.domain;

import java.util.List;

/**
 * The type Attribute record.
 */
public record AttributeRecord(String name, String path, List<String> verbs) {
}
