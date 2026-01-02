package com.generalassembly.todo.items;

import com.generalassembly.todo.categories.CategoryService;
import com.generalassembly.todo.items.dtos.CreateItemRequest;
import com.generalassembly.todo.items.dtos.ItemDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
