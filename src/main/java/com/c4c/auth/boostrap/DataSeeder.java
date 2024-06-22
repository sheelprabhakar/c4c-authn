package com.c4c.auth.boostrap;

import static com.c4c.auth.common.Constants.ROLE_ADMIN;
import static com.c4c.auth.common.Constants.ROLE_SUPER_ADMIN;
import static com.c4c.auth.common.Constants.ROLE_USER;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.dtos.CreateRoleDto;
import com.c4c.auth.core.models.dtos.CreateUserDto;
import com.c4c.auth.core.services.api.PermissionLoader;
import com.c4c.auth.core.services.api.RoleService;
import com.c4c.auth.core.services.api.UserService;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {
  private final RoleService roleService;

  private final UserService userService;

  private final PermissionLoader permissionLoader;

  public DataSeeder(RoleService roleService, UserService userService,
                    PermissionLoader permissionLoader) {
    this.roleService = roleService;
    this.userService = userService;
    this.permissionLoader = permissionLoader;
  }

  @SneakyThrows
  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    loadRoles();

    permissionLoader.load();

    loadUsers();
  }

  private void loadRoles() {
    Map<String, String> rolesMap = new HashMap<>();
    rolesMap.put(ROLE_USER, "User role");
    rolesMap.put(ROLE_ADMIN, "Admin role");
    rolesMap.put(ROLE_SUPER_ADMIN, "Super admin role");

    rolesMap.forEach((key, value) -> {
      try {
        roleService.findByName(key);
      } catch (ResourceNotFoundException e) {
        CreateRoleDto createRoleDto = new CreateRoleDto();

        createRoleDto.setName(key).setDescription(value).setDefault(true);

        roleService.save(createRoleDto);
      }
    });
  }

  private void loadUsers() throws ResourceNotFoundException {
    CreateUserDto superAdmin =
        new CreateUserDto().setEmail("sadmin@authoz.com").setFirstName("Super").setLastName("Admin")
            .setConfirmed(true).setEnabled(true).setAvatar(null).setGender("M")
            .setTimezone("Europe/Paris").setCoordinates(null).setPassword("secret");

    try {
      userService.findByEmail(superAdmin.getEmail());
    } catch (ResourceNotFoundException e) {
      superAdmin.setRole(roleService.findByName(ROLE_SUPER_ADMIN));

      userService.save(superAdmin);
    }
  }
}
