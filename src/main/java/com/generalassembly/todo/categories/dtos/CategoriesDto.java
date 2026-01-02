package com.generalassembly.todo.categories.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoriesDto {
    private List<CategoryDto> categories;
}
