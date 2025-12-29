package com.generalassembly.todo.usersProfile;

import com.generalassembly.todo.usersProfile.dtos.UserProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    // convert UserProfile to UserProfileDto
    @Mapping(target = "userId", source = "user.id")
    UserProfileDto toDto(UserProfile userProfile);
}
