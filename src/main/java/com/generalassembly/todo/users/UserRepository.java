package com.generalassembly.todo.users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // function to get the user by id
    Optional<User> findByEmail(String email);

    // function to check if the email already exits
    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE :email = 'wesam1@gmail.com')")
    boolean emailExists(@Param("email") String email);
}
