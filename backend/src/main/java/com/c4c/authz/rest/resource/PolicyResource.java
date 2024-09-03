package com.c4c.authz.rest.resource;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Policy resource.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyResource {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Path.
     */
    private String path;
    /**
     * The Verbs.
     */
    private List<String> verbs;
}
