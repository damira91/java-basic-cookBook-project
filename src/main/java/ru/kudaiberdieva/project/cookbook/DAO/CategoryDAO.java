package ru.kudaiberdieva.project.cookbook.DAO;

import ru.kudaiberdieva.project.cookbook.entity.Category;

import java.util.List;

public interface CategoryDAO {
    Category addCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int categoryId);

    List<Category> getAllCategory();

    Category getCategoryById(int id);

}