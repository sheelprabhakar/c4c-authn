package com.c4c.authz.core.domain;

import java.util.List;

/**
 * The type Attribute record.
 */
public record PolicyRecord(String name, String path, List<String> verbs) {
}
