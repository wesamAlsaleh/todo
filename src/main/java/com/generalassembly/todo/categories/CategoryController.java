package com.generalassembly.todo.categories;

import com.generalassembly.todo.categories.dtos.CreateCategoryRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    // create category endpoint
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CreateCategoryRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        // try to create category record
        try {
            // create category
            var categoryDto = categoryService.createCategory(request);

            // create the URI  to return it in the response body
            var uri = uriComponentsBuilder.path("/categories/{id}").buildAndExpand(categoryDto.getId()).toUri();

            // return the response with status 201 and the uri (location of the created entity)
            return ResponseEntity.created(uri).body(categoryDto);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating category");
        }
    }

    // get user categories endpoint
    @GetMapping("/read")
    public ResponseEntity<?> getCategories() {
        // try to get the user categories
        try {
            // get the user categories
            var categoriesDtos = categoryService.getCategories();

            // return the categories as the body
            return ResponseEntity.ok(categoriesDtos);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while getting categories");
        }
    }


    // update category by id endpoint

    // delete category by id endpoint
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        // try to delete a category by category id
        try {
            // delete the category from the system
            var deletedCategoryDto = categoryService.deleteCategory(id);

            // return response with the category as body
            return ResponseEntity.ok(deletedCategoryDto);

        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("Error while deleting category");
        }
    }
}
