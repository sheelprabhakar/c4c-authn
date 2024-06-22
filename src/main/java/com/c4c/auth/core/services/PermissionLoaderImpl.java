package com.c4c.auth.core.services;

import com.c4c.auth.common.utils.JsonUtils;
import com.c4c.auth.core.models.dtos.PermissionLoadDto;
import com.c4c.auth.core.models.entities.Permission;
import com.c4c.auth.core.models.entities.Role;
import com.c4c.auth.core.models.enums.PermissionLoadMode;
import com.c4c.auth.core.repositories.PermissionRepository;
import com.c4c.auth.core.repositories.RoleRepository;
import com.c4c.auth.core.services.api.PermissionLoader;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class PermissionLoaderImpl implements PermissionLoader {

  private final Log logger = LogFactory.getLog(this.getClass());
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  @Value("${app.permission.load.mode}")
  private PermissionLoadMode loadMode;

  public PermissionLoaderImpl(RoleRepository roleRepository,
                              PermissionRepository permissionRepository) {
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
  }

  private void addPermissionToRole(Permission permission, String[] roleNames) {
    Arrays.stream(roleNames).parallel().forEach(roleName -> {
      Optional<Role> role = roleRepository.findByName(roleName);

      role.ifPresent(roleFound -> {
        if (!roleFound.hasPermission(permission.getName())) {
          roleFound.addPermission(permission);

          roleRepository.save(roleFound);
        }
      });
    });
  }

  private void loadPermissions(List<PermissionLoadDto> permissionLoadDtoList) {
    permissionLoadDtoList.parallelStream().forEach(permissionLoadDto -> {
      Permission permissionCreated;
      Optional<Permission> permission =
          permissionRepository.findByName(permissionLoadDto.getName());

      if (permission.isEmpty()) {
        permissionCreated = permissionRepository.save(permissionLoadDto.toPermission());
      } else {
        permissionCreated = permission.get();
      }

      addPermissionToRole(permissionCreated, permissionLoadDto.getRoleNames());
    });
  }

  @Override
  public void load() {
    List<PermissionLoadDto> permissionLoadDtoList;

    if (loadMode.equals(PermissionLoadMode.CREATE)) {
      permissionRepository.deleteAll();
    }

    Resource resource = new ClassPathResource("permission.json");

    try (InputStream inputStream = resource.getInputStream()) {

      byte[] binaryData = FileCopyUtils.copyToByteArray(inputStream);
      String data = new String(binaryData, StandardCharsets.UTF_8);

      permissionLoadDtoList =
          JsonUtils.getMapper().readValue(data, new TypeReference<List<PermissionLoadDto>>() {
          });

      loadPermissions(permissionLoadDtoList);
    } catch (IOException ignored) {
      logger.error("Loading permissions: failed to read permission file!");
    }
  }
}
