package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.RestResourceEntity;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface RestResource repository.
 */
@Repository
public interface RestResourceRepository extends CrudRepository<RestResourceEntity, UUID> {
    Page<RestResourceEntity> findAll(Pageable pageable);
}
