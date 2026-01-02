package com.generalassembly.todo.items;

import com.generalassembly.todo.items.dtos.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto toDto(Item item);
}
