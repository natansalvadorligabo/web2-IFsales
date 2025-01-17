package br.com.ifsales.dao.mock;

import br.com.ifsales.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryMockDao {

    public Optional<Category> getCategoryById(Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName("MockCategoryName");
        category.setDescription("MockDescription");

        return Optional.of(category);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            Category category = new Category();
            category.setId(i);
            category.setName("MockCategoryName" + i);
            category.setDescription("MockDescription" + i);

            categories.add(category);
        }

        return categories;
    }
}