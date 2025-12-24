package com.generalassembly.todo.users;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // function to get the user by id
    Optional<User> findByEmail(String email);
}
