package com.c4c.authz.core.repository.lookup;

import com.c4c.authz.core.entity.lookup.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Country repository.
 */
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
}
