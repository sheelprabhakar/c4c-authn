package com.c4c.authn.rest.resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.util.Assert;

import java.util.List;

/**
 * The type Paged model response.
 *
 * @param <T> the type parameter
 */
@Getter
@Setter
public class PagedModelResponse<T> {
    /**
     * The Content.
     */
    private List<T> content;
    /**
     * The Page.
     */
    private PagedModel.PageMetadata page;

    /**
     * Instantiates a new Paged model response.
     */
    public PagedModelResponse() {

    }

    /**
     * Instantiates a new Paged model response.
     *
     * @param page the page
     */
    public PagedModelResponse(Page<T> page) {
        Assert.notNull(page, "Page must not be null");
        this.content = page.getContent();
        this.page = new PagedModel.PageMetadata(page.getSize(),page.getNumber(), page.getTotalElements(),
                page.getTotalPages());
    }

    /**
     * The type Page metadata.
     */
    public static record PageMetadata(long size, long number, long totalElements, long totalPages) {

    }
}
