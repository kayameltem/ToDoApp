package com.example.todoapp.service;

import com.example.todoapp.dto.UserDto;
import com.example.todoapp.model.User;

/**
 * Service interface for managing {@link User} entities.
 * <p>
 * This interface provides methods for managing user-related operations such as saving users and retrieving users by username.
 * </p>
 */
public interface IUserService {

    /**
     * Saves a new user based on the provided user DTO.
     *
     * @param userDto the user DTO containing user information to be saved
     * @return the saved user entity
     */
    User save(UserDto userDto);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved
     * @return the user with the specified username, or {@code null} if no user is found
     */
    User findByUsername(String username);
}
