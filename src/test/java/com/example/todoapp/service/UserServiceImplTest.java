package com.example.todoapp.service;

import com.example.todoapp.ToDoApplication;
import com.example.todoapp.dto.UserDto;
import com.example.todoapp.model.User;
import com.example.todoapp.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest(classes = ToDoApplication.class)
public class UserServiceImplTest {

    private IUserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(IUserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(passwordEncoder,userRepository);

    }

    @Test
    public void testSaveUser() {
        UserDto userDto = new UserDto("testuser", "testpass");
        User user = new User("testuser", "encodedpass");

        when(passwordEncoder.encode("testpass")).thenReturn("encodedpass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.save(userDto);

        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindByUsername() {
        User user = new User("testuser", "testpass");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");

        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }
}
