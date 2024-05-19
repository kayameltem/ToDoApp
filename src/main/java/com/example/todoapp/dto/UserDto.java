package com.example.todoapp.dto;

import com.example.todoapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user information.
 * <p>
 * This DTO is used to transfer user data between processes,
 * such as during registration and login.
 * </p>
 *
 * <p>
 * It includes a username and a password.
 * </p>
 *
 * @see User
 */
@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

}
