package com.generalassembly.todo.items.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateItemRequest {
    @NotBlank(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;
    private String description; // optional field
}
