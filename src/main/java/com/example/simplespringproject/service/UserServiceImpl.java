package com.example.simplespringproject.service;

import com.example.simplespringproject.dto.UserDto;
import com.example.simplespringproject.model.User;
import com.example.simplespringproject.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IUserService} for managing {@link User} entities.
 * <p>
 * This service provides methods for saving users and retrieving users by username.
 * </p>
 */
@Service
public class UserServiceImpl implements IUserService{

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

    /**
     * Constructs a new UserServiceImpl with the specified password encoder and user repository.
     *
     * @param passwordEncoder the password encoder
     * @param userRepository the user repository
     */
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, IUserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Saves a new user based on the provided user DTO.
     * The user's password is encoded before being saved.
     *
     * @param userDto the user DTO containing user information to be saved
     * @return the saved user entity
     */
    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved
     * @return the user with the specified username, or {@code null} if no user is found
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
