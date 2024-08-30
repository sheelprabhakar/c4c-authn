package com.c4c.authz.core.repository.lookup;

import com.c4c.authz.core.entity.lookup.StateEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface State repository.
 */
public interface StateRepository extends JpaRepository<StateEntity, Integer> {
  /**
   * Find by country id list.
   *
   * @param countryId the country id
   * @return the list
   */
  List<StateEntity> findByCountryId(int countryId);
}
