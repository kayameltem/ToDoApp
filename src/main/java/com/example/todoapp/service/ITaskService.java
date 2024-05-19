package com.example.todoapp.service;

import com.example.todoapp.model.Task;
import java.util.List;

/**
 * Service interface for managing {@link Task} entities.
 * <p>
 * This interface provides methods for managing tasks associated with users.
 * </p>
 */
public interface ITaskService {

    /**
     * Retrieves a list of tasks associated with a specific user ID.
     *
     * @param userId the ID of the user whose tasks are to be retrieved
     * @return a list of tasks associated with the specified user ID
     */
    List<Task> getTasksByUserId(Long userId);

    /**
     * Adds a new task to a specific user.
     *
     * @param userId the ID of the user to whom the task is to be added
     * @param task the task to be added
     * @return the added task
     */
    Task addTaskToUser(Long userId, Task task);

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to be deleted
     */
    void deleteById(Long id);

    /**
     * Toggles the completion status of a task by its ID.
     *
     * @param id the ID of the task whose completion status is to be toggled
     * @return the updated task with the toggled completion status
     */
    Task toggleTaskCompletion(Long id);

}
