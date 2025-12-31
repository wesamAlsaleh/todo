package com.generalassembly.todo.usersProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // function to check if the user has a profile
    @Query("SELECT EXISTS (SELECT 1 FROM UserProfile WHERE :userId = id)")
    boolean existByUserId(long userId);

    // function to get the user profile by user id
    Optional<UserProfile> findByUserId(long userId);
}
