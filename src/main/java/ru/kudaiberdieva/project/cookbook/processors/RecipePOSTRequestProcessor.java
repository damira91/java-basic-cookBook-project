package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.kudaiberdieva.project.cookbook.DAO.RecipeDAOImpl;
import ru.kudaiberdieva.project.cookbook.entity.Recipe;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

public class RecipePOSTRequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RecipeDAOImpl recipeDAOImpl = new RecipeDAOImpl();
    private Gson gson;

    public RecipePOSTRequestProcessor() {
        gson = new Gson();
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String requestBody = request.getBody();
        Recipe recipe = gson.fromJson(requestBody, Recipe.class);

        String recipeName = recipe.getRecipeName();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();
        int quantity = recipe.getQuantity();
        int categoryId = recipe.getCategoryId();

        Recipe newRecipe = new Recipe(recipeName, ingredients, instructions, quantity, categoryId);
        newRecipe.setRecipeName(recipeName);
        newRecipe.setIngredients(ingredients);
        newRecipe.setInstructions(instructions);
        newRecipe.setQuantity(quantity);
        newRecipe.setCategoryId(categoryId);

        Recipe addedRecipe = recipeDAOImpl.createRecipe(newRecipe);

        String jsonResponse = objectMapper.writeValueAsString(addedRecipe);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
        output.write(response.getBytes());
    }
}
