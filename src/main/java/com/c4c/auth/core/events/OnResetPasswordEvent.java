package com.c4c.auth.core.events;

import com.c4c.auth.core.models.entities.User;
import org.springframework.context.ApplicationEvent;

/**
 * The type OnResetPasswordEvent.
 */
public class OnResetPasswordEvent extends ApplicationEvent {
  private User user;

  /**
   * Instantiates a new On reset password event.
   *
   * @param user the user
   */
  public OnResetPasswordEvent(User user) {
    super(user);

    this.user = user;
  }

  /**
   * Gets user.
   *
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets user.
   *
   * @param user the user
   * @return the user
   */
  public OnResetPasswordEvent setUser(User user) {
    this.user = user;
    return this;
  }
}
