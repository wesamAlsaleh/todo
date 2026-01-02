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
            @PathVariable(name = "id") Long categoryId,
            @Valid @RequestBody CreateItemRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        // create item record
        var itemDto = itemService.createItem(
                categoryId,
                request
        );

        // create the URI to return it in the response body
        var uri = uriComponentsBuilder.path("/items/{id}").buildAndExpand(itemDto.getId()).toUri();

        // return the response with status 201 and the uri (location of the created entity)
        return ResponseEntity.created(uri).body(itemDto);
    }

    // get item endpoint
    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(
            @PathVariable Long id) {
        // fetch the item
        var itemDto = itemService.getItem(id);

        // return it in the body
        return ResponseEntity.ok(itemDto);
    }

    // update item endpoint
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(
            @Valid @RequestBody UpdateItemRequest request,
            @PathVariable Long id
    ) {
        // update the item
        var itemDto = itemService.updateItem(id, request);

        // return the updated entity
        return ResponseEntity.ok(itemDto);
    }

    // delete item endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {

        // delete the item
        var itemDto = itemService.deleteItem(id);

        // return the deleted item as the response body
        return ResponseEntity.ok(itemDto);
    }
}
