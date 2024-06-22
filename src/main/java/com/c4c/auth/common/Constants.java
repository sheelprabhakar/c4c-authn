package com.c4c.auth.common;

/**
 * The type Constants.
 */
public class Constants {
  /**
   * The constant TOKEN_PREFIX.
   */
  public static final String TOKEN_PREFIX = "Bearer ";
  /**
   * The constant HEADER_STRING.
   */
  public static final String HEADER_STRING = "Authorization";
  /**
   * The constant ROLE_USER.
   */
  public static final String ROLE_USER = "ROLE_USER";
  /**
   * The constant ROLE_ADMIN.
   */
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  /**
   * The constant ROLE_SUPER_ADMIN.
   */
  public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
  /**
   * The constant JWT_ILLEGAL_ARGUMENT_MESSAGE.
   */
  public static final String JWT_ILLEGAL_ARGUMENT_MESSAGE =
      "An error occured during getting username from token";
  /**
   * The constant JWT_EXPIRED_MESSAGE.
   */
  public static final String JWT_EXPIRED_MESSAGE = "The token is expired and not valid anymore";
  /**
   * The constant JWT_SIGNATURE_MESSAGE.
   */
  public static final String JWT_SIGNATURE_MESSAGE =
      "Authentication Failed. Username or Password not valid.";
  /**
   * The constant UNAUTHORIZED_MESSAGE.
   */
  public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
  /**
   * The constant FORBIDDEN_MESSAGE.
   */
  public static final String FORBIDDEN_MESSAGE =
      "You don't have the right to access to this resource";
  /**
   * The constant RESOURCE_NOT_FOUND_MESSAGE.
   */
  public static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource not found.";
  /**
   * The constant INVALID_DATA_MESSAGE.
   */
  public static final String INVALID_DATA_MESSAGE =
      "One or many parameters in the request's body are invalid";
  /**
   * The constant MESSAGE_KEY.
   */
  public static final String MESSAGE_KEY = "message";
  /**
   * The constant DATA_KEY.
   */
  public static final String DATA_KEY = "message";
  /**
   * The constant INVALID_TOKEN_MESSAGE.
   */
  public static final String INVALID_TOKEN_MESSAGE = "The token is invalid!";
  /**
   * The constant TOKEN_EXPIRED_MESSAGE.
   */
  public static final String TOKEN_EXPIRED_MESSAGE = "You token has been expired!";
  /**
   * The constant ACCOUNT_DEACTIVATED_MESSAGE.
   */
  public static final String ACCOUNT_DEACTIVATED_MESSAGE = "Your account has been deactivated!";
  /**
   * The constant ACCOUNT_NOT_CONFIRMED_MESSAGE.
   */
  public static final String ACCOUNT_NOT_CONFIRMED_MESSAGE = "Your account isn't confirmed yet!";
  /**
   * The constant ACCOUNT_CONFIRMED_MESSAGE.
   */
  public static final String ACCOUNT_CONFIRMED_MESSAGE = "Your account confirmed successfully!";
  /**
   * The constant NO_USER_FOUND_WITH_EMAIL_MESSAGE.
   */
  public static final String NO_USER_FOUND_WITH_EMAIL_MESSAGE = "No user found with this email!";
  /**
   * The constant PASSWORD_LINK_SENT_MESSAGE.
   */
  public static final String PASSWORD_LINK_SENT_MESSAGE =
      "A password reset link has been sent to your email box!";
  /**
   * The constant RESET_PASSWORD_SUCCESS_MESSAGE.
   */
  public static final String RESET_PASSWORD_SUCCESS_MESSAGE =
      "Your password has been resetted successfully!";
  /**
   * The constant VALIDATE_TOKEN_SUCCESS_MESSAGE.
   */
  public static final String VALIDATE_TOKEN_SUCCESS_MESSAGE = "valid";
  /**
   * The constant TOKEN_NOT_FOUND_MESSAGE.
   */
  public static final String TOKEN_NOT_FOUND_MESSAGE = "You token has been expired!";
  /**
   * The constant PASSWORD_NOT_MATCH_MESSAGE.
   */
  public static final String PASSWORD_NOT_MATCH_MESSAGE = "The current password don't match!";
  /**
   * The constant USER_PICTURE_NO_ACTION_MESSAGE.
   */
  public static final String USER_PICTURE_NO_ACTION_MESSAGE = "Unknown action!";
  /**
   * The constant ROLE_NOT_FOUND_MESSAGE.
   */
  public static final String ROLE_NOT_FOUND_MESSAGE = "Role not found!";
  /**
   * The constant PERMISSION_NOT_FOUND_MESSAGE.
   */
  public static final String PERMISSION_NOT_FOUND_MESSAGE = "Permission not found!";
  /**
   * The constant USER_NOT_FOUND_MESSAGE.
   */
  public static final String USER_NOT_FOUND_MESSAGE = "User not found!";
  /**
   * The constant SWG_AUTH_TAG_NAME.
   */
  public static final String SWG_AUTH_TAG_NAME = "Registration & Authentication";
  /**
   * The constant SWG_AUTH_TAG_DESCRIPTION.
   */
  public static final String SWG_AUTH_TAG_DESCRIPTION =
      "Operations pertaining to registration, authentication and account confirmation";
  /**
   * The constant SWG_AUTH_REGISTER_OPERATION.
   */
  public static final String SWG_AUTH_REGISTER_OPERATION = "Register a new user in the system";
  /**
   * The constant SWG_AUTH_REGISTER_MESSAGE.
   */
  public static final String SWG_AUTH_REGISTER_MESSAGE = "User registered successfully!";
  /**
   * The constant SWG_AUTH_REGISTER_ERROR.
   */
  public static final String SWG_AUTH_REGISTER_ERROR = "Failed to register the user";
  /**
   * The constant SWG_AUTH_LOGIN_OPERATION.
   */
  public static final String SWG_AUTH_LOGIN_OPERATION = "Authenticate an user";
  /**
   * The constant SWG_AUTH_LOGIN_MESSAGE.
   */
  public static final String SWG_AUTH_LOGIN_MESSAGE = "Authenticated successfully!";
  /**
   * The constant SWG_AUTH_LOGIN_ERROR.
   */
  public static final String SWG_AUTH_LOGIN_ERROR =
      "Bad credentials | The account is deactivated | The account isn't confirmed yet";
  /**
   * The constant SWG_AUTH_CONFIRM_ACCOUNT_OPERATION.
   */
  public static final String SWG_AUTH_CONFIRM_ACCOUNT_OPERATION = "Confirm the account of an user";
  /**
   * The constant SWG_AUTH_CONFIRM_ACCOUNT_MESSAGE.
   */
  public static final String SWG_AUTH_CONFIRM_ACCOUNT_MESSAGE = "Account confirmed successfully!";
  /**
   * The constant SWG_AUTH_CONFIRM_ACCOUNT_ERROR.
   */
  public static final String SWG_AUTH_CONFIRM_ACCOUNT_ERROR =
      "The token is invalid | The token has been expired";
  /**
   * The constant SWG_RESPWD_TAG_NAME.
   */
  public static final String SWG_RESPWD_TAG_NAME = "Password Reset";
  /**
   * The constant SWG_RESPWD_TAG_DESCRIPTION.
   */
  public static final String SWG_RESPWD_TAG_DESCRIPTION =
      "Operations pertaining to user's reset password process";
  /**
   * The constant SWG_RESPWD_FORGOT_OPERATION.
   */
  public static final String SWG_RESPWD_FORGOT_OPERATION = "Request a link to reset the password";
  /**
   * The constant SWG_RESPWD_FORGOT_MESSAGE.
   */
  public static final String SWG_RESPWD_FORGOT_MESSAGE =
      "Reset link sent to the mail box successfully!";
  /**
   * The constant SWG_RESPWD_FORGOT_ERROR.
   */
  public static final String SWG_RESPWD_FORGOT_ERROR = "No user found with the email provided";
  /**
   * The constant SWG_RESPWD_RESET_OPERATION.
   */
  public static final String SWG_RESPWD_RESET_OPERATION =
      "Change the user password through a reset token";
  /**
   * The constant SWG_RESPWD_RESET_MESSAGE.
   */
  public static final String SWG_RESPWD_RESET_MESSAGE = "The action completed successfully!";
  /**
   * The constant SWG_RESPWD_RESET_ERROR.
   */
  public static final String SWG_RESPWD_RESET_ERROR = "The token is invalid or has expired";
  /**
   * The constant SWG_TOKEN_TAG_NAME.
   */
  public static final String SWG_TOKEN_TAG_NAME = "Token";
  /**
   * The constant SWG_TOKEN_TAG_DESCRIPTION.
   */
  public static final String SWG_TOKEN_TAG_DESCRIPTION = "Token validation and refresh";
  /**
   * The constant SWG_TOKEN_VALIDATE_OPERATION.
   */
  public static final String SWG_TOKEN_VALIDATE_OPERATION = "Validate a token";
  /**
   * The constant SWG_TOKEN_VALIDATE_MESSAGE.
   */
  public static final String SWG_TOKEN_VALIDATE_MESSAGE = "The token is valid";
  /**
   * The constant SWG_TOKEN_VALIDATE_ERROR.
   */
  public static final String SWG_TOKEN_VALIDATE_ERROR = "Invalid token | The token has expired";
  /**
   * The constant SWG_TOKEN_REFRESH_OPERATION.
   */
  public static final String SWG_TOKEN_REFRESH_OPERATION = "Refresh token by generating new one";
  /**
   * The constant SWG_TOKEN_REFRESH_MESSAGE.
   */
  public static final String SWG_TOKEN_REFRESH_MESSAGE = "New access token generated successfully";
  /**
   * The constant SWG_TOKEN_REFRESH_ERROR.
   */
  public static final String SWG_TOKEN_REFRESH_ERROR = "Invalid token | The token is unallocated";
  /**
   * The constant SWG_USER_TAG_NAME.
   */
  public static final String SWG_USER_TAG_NAME = "Users";
  /**
   * The constant SWG_USER_TAG_DESCRIPTION.
   */
  public static final String SWG_USER_TAG_DESCRIPTION = "Manage users";
  /**
   * The constant SWG_USER_LIST_OPERATION.
   */
  public static final String SWG_USER_LIST_OPERATION = "Get all users";
  /**
   * The constant SWG_USER_LIST_MESSAGE.
   */
  public static final String SWG_USER_LIST_MESSAGE = "List retrieved successfully!";
  /**
   * The constant SWG_USER_LOGGED_OPERATION.
   */
  public static final String SWG_USER_LOGGED_OPERATION = "Get the authenticated user";
  /**
   * The constant SWG_USER_LOGGED_MESSAGE.
   */
  public static final String SWG_USER_LOGGED_MESSAGE = "User retrieved successfully!";
  /**
   * The constant SWG_USER_ITEM_OPERATION.
   */
  public static final String SWG_USER_ITEM_OPERATION = "Get one user";
  /**
   * The constant SWG_USER_ITEM_MESSAGE.
   */
  public static final String SWG_USER_ITEM_MESSAGE = "Item retrieved successfully!";
  /**
   * The constant SWG_USER_UPDATE_OPERATION.
   */
  public static final String SWG_USER_UPDATE_OPERATION = "Update a user";
  /**
   * The constant SWG_USER_UPDATE_MESSAGE.
   */
  public static final String SWG_USER_UPDATE_MESSAGE = "User updated successfully!";
  /**
   * The constant SWG_USER_UPDATE_PWD_OPERATION.
   */
  public static final String SWG_USER_UPDATE_PWD_OPERATION = "Update user password";
  /**
   * The constant SWG_USER_UPDATE_PWD_MESSAGE.
   */
  public static final String SWG_USER_UPDATE_PWD_MESSAGE = "The password updated successfully!";
  /**
   * The constant SWG_USER_UPDATE_PWD_ERROR.
   */
  public static final String SWG_USER_UPDATE_PWD_ERROR = "The current password is invalid";
  /**
   * The constant SWG_USER_DELETE_OPERATION.
   */
  public static final String SWG_USER_DELETE_OPERATION = "Delete a user";
  /**
   * The constant SWG_USER_DELETE_MESSAGE.
   */
  public static final String SWG_USER_DELETE_MESSAGE = "User deleted successfully!";
  /**
   * The constant SWG_USER_PICTURE_OPERATION.
   */
  public static final String SWG_USER_PICTURE_OPERATION = "Change or delete user picture";
  /**
   * The constant SWG_USER_PICTURE_MESSAGE.
   */
  public static final String SWG_USER_PICTURE_MESSAGE = "The picture updated/deleted successfully!";
  /**
   * The constant SWG_USER_PICTURE_ERROR.
   */
  public static final String SWG_USER_PICTURE_ERROR = "An IOException occurred!";
  /**
   * The constant SWG_USER_PERMISSION_ASSIGN_OPERATION.
   */
  public static final String SWG_USER_PERMISSION_ASSIGN_OPERATION = "Assign permissions to user";
  /**
   * The constant SWG_USER_PERMISSION_ASSIGN_MESSAGE.
   */
  public static final String SWG_USER_PERMISSION_ASSIGN_MESSAGE =
      "Permissions successfully assigned to user!";
  /**
   * The constant SWG_USER_PERMISSION_REVOKE_OPERATION.
   */
  public static final String SWG_USER_PERMISSION_REVOKE_OPERATION = "Revoke permissions to user";
  /**
   * The constant SWG_USER_PERMISSION_REVOKE_MESSAGE.
   */
  public static final String SWG_USER_PERMISSION_REVOKE_MESSAGE =
      "Permissions successfully revoked to user!";
  /**
   * The constant SWG_ROLE_TAG_NAME.
   */
  public static final String SWG_ROLE_TAG_NAME = "Roles";
  /**
   * The constant SWG_ROLE_TAG_DESCRIPTION.
   */
  public static final String SWG_ROLE_TAG_DESCRIPTION = "Manage roles";
  /**
   * The constant SWG_ROLE_CREATE_OPERATION.
   */
  public static final String SWG_ROLE_CREATE_OPERATION = "Create a role";
  /**
   * The constant SWG_ROLE_CREATE_MESSAGE.
   */
  public static final String SWG_ROLE_CREATE_MESSAGE = "Role created successfully!";
  /**
   * The constant SWG_ROLE_LIST_OPERATION.
   */
  public static final String SWG_ROLE_LIST_OPERATION = "Get all roles";
  /**
   * The constant SWG_ROLE_LIST_MESSAGE.
   */
  public static final String SWG_ROLE_LIST_MESSAGE = "List retrieved successfully!";
  /**
   * The constant SWG_ROLE_ITEM_OPERATION.
   */
  public static final String SWG_ROLE_ITEM_OPERATION = "Get one role";
  /**
   * The constant SWG_ROLE_ITEM_MESSAGE.
   */
  public static final String SWG_ROLE_ITEM_MESSAGE = "Item retrieved successfully!";
  /**
   * The constant SWG_ROLE_UPDATE_OPERATION.
   */
  public static final String SWG_ROLE_UPDATE_OPERATION = "Update a role";
  /**
   * The constant SWG_ROLE_UPDATE_MESSAGE.
   */
  public static final String SWG_ROLE_UPDATE_MESSAGE = "Role updated successfully!";
  /**
   * The constant SWG_ROLE_DELETE_OPERATION.
   */
  public static final String SWG_ROLE_DELETE_OPERATION = "Delete a role";
  /**
   * The constant SWG_ROLE_DELETE_MESSAGE.
   */
  public static final String SWG_ROLE_DELETE_MESSAGE = "Role deleted successfully!";
  /**
   * The constant SWG_ROLE_ASSIGN_PERMISSION_OPERATION.
   */
  public static final String SWG_ROLE_ASSIGN_PERMISSION_OPERATION = "Add permissions to a role";
  /**
   * The constant SWG_ROLE_ASSIGN_PERMISSION_MESSAGE.
   */
  public static final String SWG_ROLE_ASSIGN_PERMISSION_MESSAGE =
      "Permissions successfully added to the role!";
  /**
   * The constant SWG_ROLE_REMOVE_PERMISSION_OPERATION.
   */
  public static final String SWG_ROLE_REMOVE_PERMISSION_OPERATION = "Remove permissions to a role";
  /**
   * The constant SWG_ROLE_REMOVE_PERMISSION_MESSAGE.
   */
  public static final String SWG_ROLE_REMOVE_PERMISSION_MESSAGE =
      "Permissions successfully removed from role!";
  /**
   * The constant SWG_PERMISSION_TAG_NAME.
   */
  public static final String SWG_PERMISSION_TAG_NAME = "Permissions";
  /**
   * The constant SWG_PERMISSION_TAG_DESCRIPTION.
   */
  public static final String SWG_PERMISSION_TAG_DESCRIPTION = "Retrieve permissions";
  /**
   * The constant SWG_PERMISSION_LIST_OPERATION.
   */
  public static final String SWG_PERMISSION_LIST_OPERATION = "Get all permissions";
  /**
   * The constant SWG_PERMISSION_LIST_MESSAGE.
   */
  public static final String SWG_PERMISSION_LIST_MESSAGE = "List retrieved successfully!";
  /**
   * The constant SWG_PERMISSION_ITEM_OPERATION.
   */
  public static final String SWG_PERMISSION_ITEM_OPERATION = "Get one permission";
  /**
   * The constant SWG_PERMISSION_ITEM_MESSAGE.
   */
  public static final String SWG_PERMISSION_ITEM_MESSAGE = "Item retrieved successfully!";
  /**
   * The constant SWG_ADMIN_TAG_NAME.
   */
  public static final String SWG_ADMIN_TAG_NAME = "Admins";
  /**
   * The constant SWG_ADMIN_TAG_DESCRIPTION.
   */
  public static final String SWG_ADMIN_TAG_DESCRIPTION = "Manage Admin users";
  /**
   * The constant SWG_ADMIN_CREATE_OPERATION.
   */
  public static final String SWG_ADMIN_CREATE_OPERATION = "Create an admin user";
  /**
   * The constant SWG_ADMIN_CREATE_MESSAGE.
   */
  public static final String SWG_ADMIN_CREATE_MESSAGE = "Admin registered successfully!";
  /**
   * The constant SWG_ADMIN_CREATE_ERROR.
   */
  public static final String SWG_ADMIN_CREATE_ERROR = "Failed to create the admin";
  /**
   * The constant SWG_ADMIN_DELETE_OPERATION.
   */
  public static final String SWG_ADMIN_DELETE_OPERATION = "Delete an admin";
  /**
   * The constant SWG_ADMIN_DELETE_MESSAGE.
   */
  public static final String SWG_ADMIN_DELETE_MESSAGE = "Admin deleted successfully!";
  /**
   * The constant TOKEN_LIFETIME_SECONDS.
   */
  public static final long TOKEN_LIFETIME_SECONDS = 24 * 60 * 60;
  /**
   * The constant AUTHORITIES_KEY.
   */
  public static final String AUTHORITIES_KEY = "scopes";
}