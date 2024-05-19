package com.example.todoapp.service;

import com.example.todoapp.model.User;
import com.example.todoapp.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of {@link UserDetailsService} for loading user-specific data.
 * <p>
 * This service retrieves user details from the database and creates a {@link CustomUserDetails} object.
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    /**
     * Constructs a new CustomUserDetailsService with the specified user repository.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user by their username.
     *
     * @param username the username of the user to be loaded
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return new CustomUserDetails(user);
    }
}
