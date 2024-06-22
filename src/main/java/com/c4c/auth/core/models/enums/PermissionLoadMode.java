package com.c4c.auth.core.models.enums;

/**
 * The enum PermissionLoadMode.
 */
public enum PermissionLoadMode {
  /**
   * Create permission load mode.
   */
  CREATE("create"),
  /**
   * Update permission load mode.
   */
  UPDATE("update");

  /**
   * The Value.
   */
  String value;

  PermissionLoadMode(String value) {
    this.value = value;
  }
}
