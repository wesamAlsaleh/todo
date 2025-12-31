package com.generalassembly.todo.users;

import com.generalassembly.todo.users.dtos.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Dto to expose the user object
    UserDto toDto(User user);
}
