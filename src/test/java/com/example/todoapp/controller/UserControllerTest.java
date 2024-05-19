package com.example.todoapp.controller;

import com.example.todoapp.ToDoApplication;
import com.example.todoapp.dto.UserDto;
import com.example.todoapp.model.User;
import com.example.todoapp.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@SpringBootTest(classes = ToDoApplication.class)
public class UserControllerTest {

    private UserController userController;
    private IUserService userService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        userService = mock(IUserService.class);
        userController = new UserController(userService);

        // Configure a ViewResolver
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetRegistrationPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void testSaveUser() throws Exception {
        UserDto userDto = new UserDto("testuser", "testpass");
        User user = new User("testuser", "encodedpass");

        when(userService.save(any(UserDto.class))).thenReturn(user);

        mockMvc.perform(post("/registration")
                        .param("username", "testuser")
                        .param("password", "testpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        verify(userService, times(1)).save(any(UserDto.class));
    }


    @Test
    public void testGetLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}

