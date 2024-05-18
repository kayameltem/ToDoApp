package com.example.simplespringproject.controller;

import com.example.simplespringproject.model.Task;
import com.example.simplespringproject.model.User;
import com.example.simplespringproject.service.ITaskService;
import com.example.simplespringproject.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Controller responsible for handling task-related requests such as viewing, adding, updating and deleting tasks.
 */
@Controller
public class TaskController {


    private final ITaskService taskService;
    private final IUserService userService;

    /**
     * Constructs a new TaskController with the specified task service and user service.
     *
     * @param taskService the task service
     * @param userService the user service
     */
    @Autowired
    public TaskController(ITaskService taskService, IUserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /**
     * Handles GET requests to the to-do list page.
     *
     * @param model the model to add attributes to
     * @param userDetails the authenticated user details
     * @return the name of the to-do list view
     */
    @GetMapping("/to-do-list")
    public String toDoListPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Task> tasks = taskService.getTasksByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("tasks", tasks);
        return "todolist";
    }

    /**
     * Handles POST requests to add a task to the user's to-do list.
     *
     * @param task the task to add
     * @param userDetails the authenticated user details
     * @return the added task
     */
    @PostMapping("/todolist")
    @ResponseBody
    public Task addTaskToUser(@RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return taskService.addTaskToUser(user.getId(), task);
    }

    /**
     * Handles DELETE requests to delete a task from the user's to-do list.
     *
     * @param id the ID of the task to delete
     * @return a ResponseEntity indicating the success of the operation
     */
    @DeleteMapping("/todolist/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles PATCH requests to toggle the completion status of a task.
     *
     * @param id the ID of the task to toggle
     * @return a ResponseEntity containing the updated task
     */
    @PatchMapping("/todolist/{id}/toggle")
    public ResponseEntity<Task> toggleTaskCompletion(@PathVariable Long id) {
        Task task = taskService.toggleTaskCompletion(id);
        return ResponseEntity.ok(task);
    }
}
