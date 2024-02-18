package ru.kudaiberdieva.project.cookbook.DAO;

import ru.kudaiberdieva.project.cookbook.entity.Category;
import ru.kudaiberdieva.project.cookbook.entity.Recipe;
import ru.kudaiberdieva.project.cookbook.utils.DBconnection;

import java.sql.*;

public class RecipeDAOImpl implements RecipeDAO {

    @Override
    public Recipe createRecipe(Recipe recipe) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        String recipeTitle = recipe.getRecipeName();
        String ingredients = recipe.getIngredients();
        int quantity = recipe.getQuantity();
        String instructions = recipe.getInstructions();
        int categoryId = recipe.getCategoryId();
        String insertRecipeQuery = "INSERT INTO recipe (recipe_name, ingredients, quantity, instructions, category_id) VALUES (?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement insertRecipe = connection.prepareStatement(insertRecipeQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertRecipe.setString(1, recipeTitle);
                insertRecipe.setString(2, ingredients);
                insertRecipe.setInt(3, quantity);
                insertRecipe.setString(4, instructions);
                insertRecipe.setInt(5, categoryId);
                insertRecipe.executeUpdate();
                try (ResultSet genKeys = insertRecipe.getGeneratedKeys()) {
                    if (genKeys.next()) {
                        int recipeID = genKeys.getInt(1);
                        recipe.setRecipeId(recipeID);
                    }
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipe;
    }

    @Override
    public void deleteRecipe(int recipeId) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        String deleteRecipeQuery = "DELETE FROM recipe WHERE recipe_id = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement deleteRecipe = connection.prepareStatement(deleteRecipeQuery)) {
                deleteRecipe.setInt(1, recipeId);
                deleteRecipe.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    @Override
    public Recipe searchRecipe(String recipeTitle) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        Recipe recipe = null;
        try (PreparedStatement searchRecipe = connection.prepareStatement("SELECT recipe.*, category.category_name " + "FROM recipe " + "JOIN category ON recipe.category_id = category.category_id " + "WHERE recipe.recipe_name LIKE ?")) {
            searchRecipe.setString(1, "%" + recipeTitle + "%");
            try (ResultSet resultSet = searchRecipe.executeQuery()) {
                if (resultSet.next()) {
                    int recipeId = resultSet.getInt("recipe_id");
                    String title = resultSet.getString("recipe_name");
                    String composition = resultSet.getString("ingredients");
                    String instructions = resultSet.getString("instructions");
                    int quantity = resultSet.getInt("quantity");
                    String categoryName = resultSet.getString("category_name");
                    Category category = new Category(categoryName);
                    recipe = new Recipe(recipeId, title, composition, instructions, quantity, category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        try {
            connection.setAutoCommit(false);
            String updateRecipeQuery = "UPDATE recipe SET recipe_name = ?, ingredients = ?, quantity =?, instructions = ?, category_id = ? WHERE recipeID = ?";
            try (PreparedStatement updateRecipe = connection.prepareStatement(updateRecipeQuery)) {
                updateRecipe.setString(1, recipe.getRecipeName());
                updateRecipe.setString(2, recipe.getIngredients());
                updateRecipe.setInt(3, recipe.getQuantity());
                updateRecipe.setString(4, recipe.getInstructions());
                updateRecipe.setInt(5, recipe.getCategoryId());
                int rowsAffected = updateRecipe.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Recipe is successfully updated");
                } else {
                    System.out.println("Failed to update recipe title. The recipe is not found.");
                }
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    @Override
    public Recipe getRecipeById(int recipeId) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        String recipeIdRequest = "SELECT recipe.*, category.category_name FROM recipe JOIN category ON recipe.category_id = category.category_id WHERE recipe.recipe_id = ?";
        try (PreparedStatement getRecipe = connection.prepareStatement(recipeIdRequest)) {
            getRecipe.setInt(1, recipeId);
            try (ResultSet resultSet = getRecipe.executeQuery()) {
                if (resultSet.next()) {
                    String recipeName = resultSet.getString("recipe_name");
                    String ingredients = resultSet.getString("ingredients");
                    int quantity = resultSet.getInt("quantity");
                    String instructions = resultSet.getString("instructions");
                    String categoryName = resultSet.getString("category_name");
                    Category category = new Category(categoryName);
                    Recipe recipe = new Recipe(recipeId, recipeName, ingredients, instructions, quantity, category);
                    recipe.setRecipeId(recipeId);
                    recipe.setRecipeName(recipeName);
                    recipe.setIngredients(ingredients);
                    recipe.setQuantity(quantity);
                    recipe.setInstructions(instructions);
                    recipe.setCategoryName(category);
                    return recipe;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
