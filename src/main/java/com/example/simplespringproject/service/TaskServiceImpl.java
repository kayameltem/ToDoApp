package com.example.simplespringproject.service;

import com.example.simplespringproject.exception.TaskNotFoundException;
import com.example.simplespringproject.exception.UserNotFoundException;
import com.example.simplespringproject.model.Task;
import com.example.simplespringproject.model.User;
import com.example.simplespringproject.repository.ITaskRepository;
import com.example.simplespringproject.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of {@link ITaskService} for managing {@link Task} entities.
 * <p>
 * This service provides methods for managing tasks associated with users, such as retrieving, adding, deleting, and toggling task completion.
 * </p>
 */
@Service
public class TaskServiceImpl implements ITaskService{


    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;

    /**
     * Constructs a new TaskServiceImpl with the specified task repository and user repository.
     *
     * @param taskRepository the task repository
     * @param userRepository the user repository
     */
    @Autowired
    public TaskServiceImpl(ITaskRepository taskRepository, IUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a list of tasks associated with a specific user ID.
     *
     * @param userId the ID of the user whose tasks are to be retrieved
     * @return a list of tasks associated with the specified user ID
     */
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    /**
     * Adds a new task to a specific user.
     *
     * @param userId the ID of the user to whom the task is to be added
     * @param task the task to be added
     * @return the added task
     * @throws UserNotFoundException if the user with the specified ID is not found
     */
    public Task addTaskToUser(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to be deleted
     */
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Toggles the completion status of a task by its ID.
     *
     * @param id the ID of the task whose completion status is to be toggled
     * @return the updated task with the toggled completion status
     * @throws TaskNotFoundException if the task with the specified ID is not found
     */
    @Override
    public Task toggleTaskCompletion(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }
}
