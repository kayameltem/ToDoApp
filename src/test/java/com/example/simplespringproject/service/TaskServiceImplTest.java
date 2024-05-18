package com.example.simplespringproject.service;

import com.example.simplespringproject.model.Task;
import com.example.simplespringproject.model.User;
import com.example.simplespringproject.repository.ITaskRepository;
import com.example.simplespringproject.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    private TaskServiceImpl taskService;

    @Mock
    private ITaskRepository taskRepository;

    @Mock
    private IUserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository, userRepository);
    }

    @Test
    public void testGetTasksByUserId() {
        Long userId = 1L;
        Task task1 = new Task("task1", new User("testuser", "password"));
        Task task2 = new Task("task2", new User("testuser", "password"));

        when(taskRepository.findByUserId(userId)).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getTasksByUserId(userId);

        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testAddTaskToUser() {
        Long userId = 1L;
        User user = new User("testuser", "password");
        Task task = new Task("new task", user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.addTaskToUser(userId, task);

        assertEquals("new task", createdTask.getDescription());
        assertEquals(user, createdTask.getUser());
        verify(userRepository, times(1)).findById(userId);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDeleteById() {
        Long taskId = 1L;

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteById(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testToggleTaskCompletion() {
        Long taskId = 1L;
        User user = new User("testuser", "password");
        Task task = new Task("task", user);
        task.setCompleted(false);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task updatedTask = taskService.toggleTaskCompletion(taskId);

        assertTrue(updatedTask.isCompleted());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testToggleTaskCompletion_taskNotFound() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.toggleTaskCompletion(taskId);
        });

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
    }
}
