package com.c4c.authz.adapter.api;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * The type Converter.
 *
 * @param <E> the type parameter
 * @param <R> the type parameter
 */
@RequiredArgsConstructor
public abstract class Converter<E, R> {

    /**
     * The From resource.
     */
    private final Function<R, E> fromResource;
    /**
     * The From entity.
     */
    private final Function<E, R> fromEntity;

    /**
     * Convert from resource e.
     *
     * @param resource the resource
     * @return the e
     */
    public final E convertFromResource(final R resource) {
    return this.fromResource.apply(resource);
  }

    /**
     * Covert from entity r.
     *
     * @param entity the entity
     * @return the r
     */
    public final R covertFromEntity(final E entity) {
    return this.fromEntity.apply(entity);
  }

    /**
     * Create from resources list.
     *
     * @param resources the resources
     * @return the list
     */
    public final List<E> createFromResources(final Collection<R> resources) {
    return resources.stream().map(this::convertFromResource).toList();
  }

    /**
     * Create from entities list.
     *
     * @param entities the entities
     * @return the list
     */
    public final List<R> createFromEntities(final Collection<E> entities) {
    return entities.stream().map(this::covertFromEntity).toList();
  }

    /**
     * Create from entities page.
     *
     * @param entities the entities
     * @return the page
     */
    public final Page<R> createFromEntities(final Page<E> entities) {
    List<R> list = entities.getContent().stream().map(this::covertFromEntity).toList();
    return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
  }
}

