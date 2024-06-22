package com.c4c.auth.core.services;

import static com.c4c.auth.common.Constants.INVALID_TOKEN_MESSAGE;
import static com.c4c.auth.common.Constants.RESOURCE_NOT_FOUND_MESSAGE;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.models.entities.UserAccount;
import com.c4c.auth.core.repositories.UserAccountRepository;
import com.c4c.auth.core.services.api.UserAccountService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;


/**
 * The type UserAccountServiceImpl.
 */
@Service(value = "userAccountService")
public class UserAccountServiceImpl implements UserAccountService {
  private final UserAccountRepository userAccountRepository;

  /**
   * Instantiates a new User account service.
   *
   * @param userAccountRepository the user account repository
   */
  public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }

  @Override
  public UserAccount save(User user, String token) {
    UserAccount newUserAccount = new UserAccount();
    Date dateNow = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(dateNow);
    c.add(Calendar.DATE, 2);

    newUserAccount.setUser(user)
        .setToken(token)
        .setExpireAt(c.getTime().getTime());

    return userAccountRepository.save(newUserAccount);
  }

  @Override
  public List<UserAccount> findAll() {
    List<UserAccount> list = new ArrayList<>();
    userAccountRepository.findAll().iterator().forEachRemaining(list::add);

    return list;
  }

  @Override
  public void delete(String id) {
    userAccountRepository.deleteById(new ObjectId(id));
  }

  @Override
  public UserAccount findByToken(String token) throws ResourceNotFoundException {
    Optional<UserAccount> userAccountOptional = userAccountRepository.findByToken(token);

    if (userAccountOptional.isEmpty()) {
      throw new ResourceNotFoundException(INVALID_TOKEN_MESSAGE);
    }

    return userAccountOptional.get();
  }

  @Override
  public UserAccount findById(String id) throws ResourceNotFoundException {
    Optional<UserAccount> confirmAccountOptional = userAccountRepository.findById(new ObjectId(id));

    if (confirmAccountOptional.isEmpty()) {
      throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE);
    }

    return confirmAccountOptional.get();
  }
}
