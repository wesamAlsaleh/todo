package com.generalassembly.todo.categories.dtos;

import com.generalassembly.todo.items.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private Long userId;
    private String categoryName;
    private String categoryDescription;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Item> items;
}
