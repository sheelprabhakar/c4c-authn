package com.c4c.auth.core.events;

import com.c4c.auth.core.models.entities.User;
import org.springframework.context.ApplicationEvent;

/**
 * The type OnRegistrationCompleteEvent.
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {
  private User user;

  /**
   * Instantiates a new On registration complete event.
   *
   * @param user the user
   */
  public OnRegistrationCompleteEvent(User user) {
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
  public OnRegistrationCompleteEvent setUser(User user) {
    this.user = user;
    return this;
  }
}
