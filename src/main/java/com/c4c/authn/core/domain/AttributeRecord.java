package com.c4c.authn.core.domain;

import java.util.BitSet;
import java.util.List;

public record AttributeRecord(String name, String path, List<String> verbs) {
}
