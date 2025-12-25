package com.generalassembly.todo.authentication;

import com.generalassembly.todo.users.User;
import com.generalassembly.todo.users.dtos.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
// MapStruct annotation to define this interface as a mapper and generate implementation at compile time
public interface AuthenticationMapper {
    // convert user object to register response dto
    UserDto toDto(User user);
}
