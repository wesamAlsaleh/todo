package com.generalassembly.todo.categories;

import com.generalassembly.todo.categories.dtos.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "categoryName", source = "name")
    @Mapping(target = "categoryDescription", source = "description")
    CategoryDto toDto(Category category);
}
