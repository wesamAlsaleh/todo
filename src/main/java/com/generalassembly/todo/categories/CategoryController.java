package com.generalassembly.todo.categories;

import com.generalassembly.todo.categories.dtos.CreateCategoryRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    // read category by id endpoint

    // update category by id endpoint

    // delete category by id endpoint
}
