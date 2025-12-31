package com.generalassembly.todo.categories.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private Long userId;
    private String categoryName;
    private String categoryDescription;
    private Instant createdAt;
    private Instant updatedAt;
}
