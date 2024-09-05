package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.RestAclEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Rest acl repository.
 */
@Repository
public interface RestAclRepository extends CrudRepository<RestAclEntity, UUID> {
  /**
   * Find all page.
   *
   * @param pageable the pageable
   * @return the page
   */
  Page<RestAclEntity> findAll(Pageable pageRequest);

  /**
   * Find all by tenant id list.
   *
   * @param tenantId the tenant id
   * @return the list
   */
  List<RestAclEntity> findAllByTenantId(UUID tenantId);

  /**
   * Find all by tenant id page.
   *
   * @param pageable the pageable
   * @param tenantId the tenant id
   * @return the page
   */
  Page<RestAclEntity> findAllByTenantId(Pageable pageRequest, UUID tenantId);
}
