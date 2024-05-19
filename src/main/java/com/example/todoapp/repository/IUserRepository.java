package com.example.todoapp.repository;

import com.example.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for {@link User} entities.
 * <p>
 * This interface provides methods for performing CRUD operations on users.
 * </p>
 *
 * <p>
 * It extends {@link JpaRepository} to leverage Spring Data JPA functionalities.
 * </p>
 */
@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved
     * @return the user with the specified username, or {@code null} if no user is found
     */
    User findByUsername(String username);

}
