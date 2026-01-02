package com.generalassembly.todo.categories.dtos;

import com.generalassembly.todo.items.dtos.ItemDto;
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
    private List<ItemDto> items; // each category has a list of items
}
