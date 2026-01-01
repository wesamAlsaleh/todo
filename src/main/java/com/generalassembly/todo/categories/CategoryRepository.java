package com.generalassembly.todo.categories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> getCategoriesByUserId(Long userId);

    Optional<Category> findById(Long id);
}
