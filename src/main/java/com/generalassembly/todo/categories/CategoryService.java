package com.generalassembly.todo.categories;

import com.generalassembly.todo.categories.dtos.CategoriesDto;
import com.generalassembly.todo.categories.dtos.CategoryDto;
import com.generalassembly.todo.categories.dtos.CreateCategoryRequest;
import com.generalassembly.todo.categories.dtos.UpdateCategoryRequest;
import com.generalassembly.todo.global.exceptions.ResourceNotFoundException;
import com.generalassembly.todo.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // function to create category
    public CategoryDto createCategory(CreateCategoryRequest request) {
        // get the authenticated user
        var user = userService.getUser();

        // create new category instance
        var category = new Category();

        // set the fields
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUser(user);

        // save the changes
        categoryRepository.save(category);

        // return the category as categoryDto
        return categoryMapper.toDto(category);
    }

    // get all categories for a user
    public CategoriesDto getCategories() {
        // get the authenticated user
        var user = userService.getUser();

        // get the categories
        var categories = categoryRepository.getCategoriesByUserId(user.getId());

        // array holder
        List<CategoryDto> categoryDtos = new ArrayList<>();

        // convert each category to categoryDto
        categories.forEach(category -> categoryDtos.add(categoryMapper.toDto(category)));

        // return
        return new CategoriesDto(categoryDtos);
    }

    // function to get category by id
    public Category getCategory(Long categoryId) {
        // get the category with the provided id
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }


    // function to update a category by id
    public CategoryDto updateCategory(Long id, UpdateCategoryRequest request) {
        // get the category with the provided id
        var category = getCategory(id);

        // extract the values
        var name = request.getName();
        var description = request.getDescription();

        // replace the provided fields with the existence fields
        if (StringUtils.hasText(name)) {
            category.setName(name);

        }
        category.setDescription(description); // not required field

        // update the changes
        categoryRepository.save(category);

        // return the updated category as Dto
        return categoryMapper.toDto(category);
    }

    // function to delete a category by id
    public CategoryDto deleteCategory(Long id) {
        // get the category with the provided id
        var category = getCategory(id);

        // delete the record from the db
        categoryRepository.delete(category);

        // return the deleted category
        return categoryMapper.toDto(category);
    }
}
