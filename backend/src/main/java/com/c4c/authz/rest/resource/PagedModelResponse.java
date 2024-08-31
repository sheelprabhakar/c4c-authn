package com.c4c.authz.rest.resource;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.util.Assert;

/**
 * The type Paged model response.
 *
 * @param <T> the type parameter
 */
@Getter
@Setter
public class PagedModelResponse<T> {
  /**
   * The Items.
   */
  private List<T> items;
  /**
   * The Total.
   */
  private int total;
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
  public PagedModelResponse(final Page<T> page) {
        Assert.notNull(page, "Page must not be null");
        this.items = page.getContent();
        this.total = this.items.size();
        this.page = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(),
                page.getTotalPages());
    }

  /**
   * The type Page metadata.
   */
  public record PageMetadata(long size, long number, long totalElements, long totalPages) {

    }
}
