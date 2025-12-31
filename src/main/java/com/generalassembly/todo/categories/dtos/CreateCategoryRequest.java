package com.generalassembly.todo.categories.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCategoryRequest {
    @NotEmpty(message = "Category name is required")
    @NotBlank(message = "Category name is required")
    @Size(min = 1, max = 255)
    private String name;

    private String description; // optional field
}
