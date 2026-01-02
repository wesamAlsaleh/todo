package com.generalassembly.todo.items;

import com.generalassembly.todo.items.dtos.CreateItemRequest;
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

    // update item endpoint

    // delete item endpoint
}
