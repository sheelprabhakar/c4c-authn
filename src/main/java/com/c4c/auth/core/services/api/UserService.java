package com.c4c.auth.core.services.api;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.dtos.CreateUserDto;
import com.c4c.auth.core.models.dtos.UpdatePasswordDto;
import com.c4c.auth.core.models.dtos.UpdateUserDto;
import com.c4c.auth.core.models.entities.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The interface UserService.
 */
public interface UserService extends UserDetailsService {
    /**
     * Save user.
     *
     * @param createUserDto the create user dto
     * @return the user
     */
    User save(CreateUserDto createUserDto);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<User> findAll();

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(String id);

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     * @throws ResourceNotFoundException the resource not found exception
     */
    User findByEmail(String email) throws ResourceNotFoundException;

    /**
     * Find by id user.
     *
     * @param id the id
     * @return the user
     * @throws ResourceNotFoundException the resource not found exception
     */
    User findById(String id) throws ResourceNotFoundException;

    /**
     * Update user.
     *
     * @param id            the id
     * @param updateUserDto the update user dto
     * @return the user
     * @throws ResourceNotFoundException the resource not found exception
     */
    User update(String id, UpdateUserDto updateUserDto) throws ResourceNotFoundException;

    /**
     * Update.
     *
     * @param user the user
     */
    void update(User user);

    /**
     * Update password user.
     *
     * @param id                the id
     * @param updatePasswordDto the update password dto
     * @return the user
     * @throws ResourceNotFoundException the resource not found exception
     */
    User updatePassword(String id, UpdatePasswordDto updatePasswordDto)
      throws ResourceNotFoundException;

    /**
     * Update password.
     *
     * @param id          the id
     * @param newPassword the new password
     * @throws ResourceNotFoundException the resource not found exception
     */
    void updatePassword(String id, String newPassword) throws ResourceNotFoundException;

    /**
     * Confirm.
     *
     * @param id the id
     * @throws ResourceNotFoundException the resource not found exception
     */
    void confirm(String id) throws ResourceNotFoundException;
}
