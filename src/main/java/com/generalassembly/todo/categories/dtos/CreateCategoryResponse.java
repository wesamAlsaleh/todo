package com.generalassembly.todo.categories.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCategoryResponse {
    private CategoryDto category;
}
