package com.c4c.auth.core.models.dtos;

import com.c4c.auth.core.models.entities.Coordinates;
import com.c4c.auth.core.models.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(name = "UpdateUserParam", description = "Parameters required to update an user")
@Accessors(chain = true)
@Setter
@Getter
public class UpdateUserDto {
  @Schema(description = "User first name")
  private String firstName;

  @Schema(description = "User last name")
  private String lastName;

  @Schema(description = "User timezone")
  private String timezone;

  @Schema(description = "User gender")
  private String gender;

  private String avatar;

  @Schema(description = "Indicates if the will be enabled or not")
  private boolean enabled;

  @Schema(description = "Indicates if has confirmed his account")
  private boolean confirmed;

  @Schema(description = "Geographic location of the user")
  private Coordinates coordinates;

  private Set<Role> roles;
}
