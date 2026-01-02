# TODO Application

This repository contains a Spring Boot Todo application developed for my homework assignment.

**Project Link:** [GitHub Repository](https://github.com/wesamAlsaleh/todo)

## 🏗 Design Decisions

* **DTO (Data Transfer Object) Pattern:** Used to decouple the database entities from the API response. This prevents internal database details (like password hashes or internal IDs) from leaking and allows for custom, clean JSON formatting.
* **Soft Relationships (Lazy Loading):** `FetchType.LAZY` is used for most relationships. This prevents "Eager" loading of the entire database into memory, ensuring the app stays performant as the data grows.
* **Global Exception Handling:** Implemented using `@RestControllerAdvice`. This ensures that if an error occurs (like a missing ID or a bad input), the user receives a clean JSON error message instead of a confusing Java stack trace.

## ✅ What Went Right

The overall architecture of the application came together smoothly. Using the **Repository Pattern** with Spring Data JPA allowed me to handle complex database interactions with minimal boilerplate code. Integrating **User Authentication** early on provided a solid security foundation, making the subsequent Category and Item features feel like a complete, private product.

## 🚧 Challenges Faced

* **The N + 1 Performance Issue:** I discovered that fetching categories was triggering a separate SQL query for every single category just to retrieve its items.
    * **The Fix:** I optimized data retrieval by using `@EntityGraph(attributePaths = {"items"})`. This tells JPA to fetch the categories and their related items in a single join query, significantly improving performance.



* **Circular References in JSON:** I encountered an infinite recursion problem where a Category would load an Item, which would then try to load its parent Category, creating an endless loop in the JSON response.
    * **The Fix:** I restructured the **DTOs** to ensure the relationship only flows in one direction during serialization, ensuring a flat and readable JSON structure.



## ❤️ Favorite Part

My favorite part was **implementing the authentication logic**. There is a great sense of satisfaction in building a secure system where data is protected. Ensuring that a user can only access and modify their own specific categories and items made the project feel like a real-world application.