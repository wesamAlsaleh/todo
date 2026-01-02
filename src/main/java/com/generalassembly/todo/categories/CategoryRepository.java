package com.generalassembly.todo.categories;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    @EntityGraph(attributePaths = "items") // required to fix the N+1 issue by joining the category and items tables and fetching the items that belong to the category
    List<Category> getCategoriesByUserId(Long userId);

    Optional<Category> findById(Long id);
}
