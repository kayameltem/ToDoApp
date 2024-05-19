package com.example.todoapp.repository;

import com.example.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository interface for {@link Task} entities.
 * <p>
 * This interface provides methods for performing CRUD operations on tasks.
 * </p>
 *
 * <p>
 * It extends {@link JpaRepository} to leverage Spring Data JPA functionalities.
 * </p>
 */
@Repository
public interface ITaskRepository extends JpaRepository<Task,Long> {
    /**
     * Retrieves a list of tasks associated with a specific user ID.
     *
     * @param userId the ID of the user whose tasks are to be retrieved
     * @return a list of tasks associated with the specified user ID
     */
    List<Task> findByUserId(Long userId);
}
