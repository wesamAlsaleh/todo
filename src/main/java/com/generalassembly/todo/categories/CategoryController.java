package com.generalassembly.todo.categories;

import com.generalassembly.todo.categories.dtos.CreateCategoryRequest;
import com.generalassembly.todo.categories.dtos.UpdateCategoryRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    // create category endpoint
    @PostMapping("/")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CreateCategoryRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        // create category
        var categoryDto = categoryService.createCategory(request);

        // create the URI to return it in the response body
        var uri = uriComponentsBuilder.path("/categories/{id}").buildAndExpand(categoryDto.getId()).toUri();

        // return the response with status 201 and the uri (location of the created entity)
        return ResponseEntity.created(uri).body(categoryDto);
    }

    // get user categories endpoint
    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        // get the user categories
        var categoriesDtos = categoryService.getCategories();

        // return the categories as the body
        return ResponseEntity.ok(categoriesDtos);
    }

    // get single category by id endpoint
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(
            @PathVariable Long id
    ) {
        // get the category
        var categoryDto = categoryService.getCategory(id);

        // return the category as the body
        return ResponseEntity.ok(categoryDto);
    }

    // get category items
    @GetMapping("/{id}/items")
    public ResponseEntity<?> getCategoryItems(
            @PathVariable Long id
    ) {
        // get the category items
        var categoryItemDtos = categoryService.getCategoryItems(id);

        // return the category items as the body
        return ResponseEntity.ok(categoryItemDtos);
    }

    // update category by id endpoint
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request
    ) {
        // update the category
        var categoryDto = categoryService.updateCategory(id, request);

        // return a success response
        return ResponseEntity.accepted().body(categoryDto);
    }

    // delete category by id endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        // delete the category from the system
        var deletedCategoryDto = categoryService.deleteCategory(id);

        // return response with the category as body
        return ResponseEntity.ok(deletedCategoryDto);
    }
}
