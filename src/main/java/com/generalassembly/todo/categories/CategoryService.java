package com.generalassembly.todo.categories;

import com.generalassembly.todo.authentication.services.AuthenticationService;
import com.generalassembly.todo.categories.dtos.CategoryDto;
import com.generalassembly.todo.categories.dtos.CreateCategoryRequest;
import com.generalassembly.todo.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
