package com.generalassembly.todo.categories;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> getCategoriesByUserId(Long userId);
}
