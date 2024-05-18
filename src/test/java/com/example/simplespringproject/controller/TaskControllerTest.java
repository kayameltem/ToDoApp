package com.example.simplespringproject.controller;

import com.example.simplespringproject.model.Task;
import com.example.simplespringproject.model.User;
import com.example.simplespringproject.service.ITaskService;
import com.example.simplespringproject.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockBean
    private ITaskService taskService;
    @MockBean
    private IUserService userService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "testuser")
    public void testToDoListPage() throws Exception {
        User user = new User("testuser", "password");
        user.setId(1L);

        Task task1 = new Task("task1", user);
        Task task2 = new Task("task2", user);

        when(userService.findByUsername("testuser")).thenReturn(user);
        when(taskService.getTasksByUserId(1L)).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/to-do-list"))
                .andExpect(status().isOk())
                .andExpect(view().name("todolist"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("tasks"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testAddTaskToUser() throws Exception {
        User user = new User("testuser", "password");
        user.setId(1L);
        Task task = new Task("new task", user);

        when(userService.findByUsername("testuser")).thenReturn(user);
        when(taskService.addTaskToUser(any(Long.class), any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/todolist")
                        .with(csrf())
                        .principal(() -> "testuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("new task"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteById(1L);

        mockMvc.perform(delete("/todolist/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteById(1L);
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testToggleTaskCompletion() throws Exception {
        User user = new User("testuser", "password");
        user.setId(1L);
        Task task = new Task("task", user);
        task.setCompleted(false);

        when(taskService.toggleTaskCompletion(1L)).thenReturn(task);

        mockMvc.perform(patch("/todolist/1/toggle")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("task"))
                .andExpect(jsonPath("$.completed").value(false));
    }
}

