package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.entity.RoleAttributeId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Role attribute repository.
 */
@Repository
public interface RoleAttributeRepository extends CrudRepository<RoleAttributeEntity, RoleAttributeId> {
  /**
   * Find all page.
   *
   * @param pageable the pageable
   * @return the page
   */
  Page<RoleAttributeEntity> findAll(Pageable pageable);

  /**
   * Find all by role id list.
   *
   * @param roleId the role id
   * @return the list
   */
  List<RoleAttributeEntity> findAllByRoleId(UUID roleId);

}
