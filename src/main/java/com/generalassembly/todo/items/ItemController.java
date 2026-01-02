package com.generalassembly.todo.items;

import com.generalassembly.todo.items.dtos.CreateItemRequest;
import com.generalassembly.todo.items.dtos.UpdateItemRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    // create item endpoint
    @PostMapping("/{id}/create")
    public ResponseEntity<?> createItem(
            @PathVariable(name = "id") String categoryId,
            @Valid @RequestBody CreateItemRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        // try to create item
        try {
            // create item record
            var itemDto = itemService.createItem(
                    Long.parseLong(categoryId),
                    request
            );

            // create the URI to return it in the response body
            var uri = uriComponentsBuilder.path("/items/{id}").buildAndExpand(itemDto.getId()).toUri();

            // return the response with status 201 and the uri (location of the created entity)
            return ResponseEntity.created(uri).body(itemDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // get item endpoint
    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(
            @PathVariable String id) {
        // try to fetch the item
        try {
            // fetch the item
            var itemDto = itemService.getItem(Long.parseLong(id));

            // return it in the body
            return ResponseEntity.ok(itemDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch the item details");
        }
    }

    // update item endpoint
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(
            @Valid @RequestBody UpdateItemRequest request,
            @PathVariable String id
    ) {
        // try to update the item
        try {
            // update the item
            var itemDto = itemService.updateItem(Long.parseLong(id), request);

            // return the updated entity
            return ResponseEntity.ok(itemDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update the item");
        }
    }

    // delete item endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable String id) {
        // try to delete item
        try {
            // delete the item
            var itemDto = itemService.deleteItem(Long.parseLong(id));

            // return the deleted item as the response body
            return ResponseEntity.ok(itemDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete the item");
        }
    }
}
