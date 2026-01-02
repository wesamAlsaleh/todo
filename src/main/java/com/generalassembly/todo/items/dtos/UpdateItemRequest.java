package com.generalassembly.todo.items.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateItemRequest {
    private String name; // optional field
    private String description; // optional field
}
