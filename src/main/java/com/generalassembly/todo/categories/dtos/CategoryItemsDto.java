package com.generalassembly.todo.categories.dtos;

import com.generalassembly.todo.items.dtos.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryItemsDto {
    private List<ItemDto> categoryItems;
}
