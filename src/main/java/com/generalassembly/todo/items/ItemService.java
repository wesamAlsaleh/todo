package com.generalassembly.todo.items;

import com.generalassembly.todo.categories.CategoryService;
import com.generalassembly.todo.global.exceptions.ResourceNotFoundException;
import com.generalassembly.todo.items.dtos.CreateItemRequest;
import com.generalassembly.todo.items.dtos.ItemDto;
import com.generalassembly.todo.items.dtos.UpdateItemRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class ItemService {
    private final CategoryService categoryService;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    // function to create item
    public ItemDto createItem(
            Long categoryId,
            CreateItemRequest request
    ) {
        // get the category
        var category = categoryService.getCategory(categoryId);

        // create the item instance
        var item = new Item();
        item.setCategory(category);
        item.setName(request.getName());
        item.setDescription(request.getDescription());

        // save the record
        itemRepository.save(item);

        // convert the item to ItemDto and return it
        return itemMapper.toDto(item);
    }

    // helper function to fetch the item from the db
    private Item fetchItem(Long id) {
        // get and return the item
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

    // function to fetch single item by id
    public ItemDto getItem(Long itemId) {
        // get the item
        var item = fetchItem(itemId);

        // return the item as Dto
        return itemMapper.toDto(item);
    }

    // function to update an item
    public ItemDto updateItem(Long itemId, UpdateItemRequest request) {
        // get the item
        var item = fetchItem(itemId);

        // replace provided fields
        if (StringUtils.hasText(request.getName())) {
            item.setName(request.getName());
        }
        item.setDescription(request.getDescription()); // optional field

        // save the changes
        itemRepository.save(item);

        // return the updated entity
        return itemMapper.toDto(item);
    }
}
