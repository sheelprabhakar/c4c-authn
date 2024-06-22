package com.c4c.auth.core.repositories;

import com.c4c.auth.core.models.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
  RefreshToken findByValue(String value);
}