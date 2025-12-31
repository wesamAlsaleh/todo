package com.generalassembly.todo.usersProfile;

import com.generalassembly.todo.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "users_profile")
public class UserProfile {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // do not fetch user details when fetching the user profile!
    @MapsId // This tells JPA: "Use the User's ID as the Primary Key for this profile"
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_description")
    private String profileDescription;
}