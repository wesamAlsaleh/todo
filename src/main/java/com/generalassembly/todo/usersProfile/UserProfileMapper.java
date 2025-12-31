package com.generalassembly.todo.usersProfile;

import com.generalassembly.todo.users.User;
import com.generalassembly.todo.users.dtos.UserDetailsDto;
import com.generalassembly.todo.usersProfile.dtos.UserProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    // convert UserProfile to UserProfileDto
    @Mapping(target = "userId", source = "user.id")
    UserProfileDto toDto(UserProfile userProfile);

    // Dto to expose user details
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "firstName", source = "userProfile.firstName")
    @Mapping(target = "lastName", source = "userProfile.lastName")
    @Mapping(target = "profileDescription", source = "userProfile.profileDescription")
    UserDetailsDto toDto(User user);
}
