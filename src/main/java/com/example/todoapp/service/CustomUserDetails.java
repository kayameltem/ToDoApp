package com.example.todoapp.service;

import com.example.todoapp.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom implementation of {@link UserDetails} for representing a user in the Spring Security context.
 * <p>
 * This class wraps a {@link User} object and provides user information to the Spring Security framework.
 * </p>
 */
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of granted authorities (always null in this implementation)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the user's password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the user's username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true (the account is not expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true (the account is not locked)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return true (the credentials are not expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true (the user is enabled)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
