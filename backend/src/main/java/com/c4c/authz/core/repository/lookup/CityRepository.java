package com.c4c.authz.core.repository.lookup;

import com.c4c.authz.core.entity.lookup.CityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface City repository.
 */
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
  /**
   * Find by state id list.
   *
   * @param stateId the state id
   * @return the list
   */
  List<CityEntity> findByStateId(int stateId);
}
