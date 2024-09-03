package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.RoleRestAclEntity;
import com.c4c.authz.core.entity.RoleRestAclId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Role rest acl repository.
 */
@Repository
public interface RoleRestAclRepository extends CrudRepository<RoleRestAclEntity, RoleRestAclId> {
  /**
   * Find all page.
   *
   * @param pageable the pageable
   * @return the page
   */
  Page<RoleRestAclEntity> findAll(Pageable pageable);

  /**
   * Find all by role id list.
   *
   * @param roleId the role id
   * @return the list
   */
  List<RoleRestAclEntity> findAllByRoleId(UUID roleId);

}
