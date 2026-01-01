package com.generalassembly.todo.categories;

import com.generalassembly.todo.authentication.services.AuthenticationService;
import com.generalassembly.todo.categories.dtos.CategoriesDto;
import com.generalassembly.todo.categories.dtos.CategoryDto;
import com.generalassembly.todo.categories.dtos.CreateCategoryRequest;
import com.generalassembly.todo.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
