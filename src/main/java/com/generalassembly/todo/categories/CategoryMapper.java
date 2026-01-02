package com.generalassembly.todo.categories;

import com.generalassembly.todo.categories.dtos.CategoryDto;
import com.generalassembly.todo.categories.dtos.CategoryItemsDto;
import com.generalassembly.todo.items.dtos.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "categoryName", source = "name")
    @Mapping(target = "categoryDescription", source = "description")
    @Mapping(target = "items", source = "items")
    CategoryDto toDto(Category category);
}
