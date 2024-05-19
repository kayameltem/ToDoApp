package com.example.todoapp.service;

import com.example.todoapp.ToDoApplication;
import com.example.todoapp.model.User;
import com.example.todoapp.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringBootTest(classes = ToDoApplication.class)
public class CustomUserDetailsServiceTest {

    private IUserRepository userRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(IUserRepository.class);
        userDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        User user = new User("testuser", "testpass");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername("testuser");

        assertEquals(user.getUsername(), userDetails.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("testuser");
        });

        verify(userRepository, times(1)).findByUsername("testuser");
    }
}

