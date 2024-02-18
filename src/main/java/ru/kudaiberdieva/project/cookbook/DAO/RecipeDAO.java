package ru.kudaiberdieva.project.cookbook.DAO;

import ru.kudaiberdieva.project.cookbook.entity.Recipe;

public interface RecipeDAO {
    Recipe createRecipe(Recipe recipe);

    void deleteRecipe(int recipeId);

    Recipe searchRecipe(String recipeTitle);

    void updateRecipe(Recipe recipe);

    Recipe getRecipeById(int recipeId);
}
