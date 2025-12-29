package com.generalassembly.todo.usersProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // function to check if the user has a profile
    @Query("SELECT EXISTS (SELECT 1 FROM UserProfile WHERE :userId = id)")
    boolean existByUserId(long userId);
}
