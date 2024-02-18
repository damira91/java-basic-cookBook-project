package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.kudaiberdieva.project.cookbook.DAO.RecipeDAOImpl;
import ru.kudaiberdieva.project.cookbook.entity.Recipe;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

public class RecipePUTRequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RecipeDAOImpl recipeDAOImpl = new RecipeDAOImpl();
    private Gson gson;

    public RecipePUTRequestProcessor() {
        gson = new Gson();
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String id = request.getParameter("id");

        try {
            int recipeId = Integer.parseInt(id);
            Recipe existRecipe = recipeDAOImpl.getRecipeById(recipeId);

            if (existRecipe != null) {
                String requestBody = request.getBody();
                Recipe updatedRecipe = gson.fromJson(requestBody, Recipe.class);
                if (updatedRecipe.getRecipeName() != null) {
                    existRecipe.setRecipeName(updatedRecipe.getRecipeName());
                }
                if (updatedRecipe.getIngredients() != null) {
                    existRecipe.setIngredients(updatedRecipe.getIngredients());
                }
                if (updatedRecipe.getQuantity() != 0) {
                    existRecipe.setQuantity(updatedRecipe.getQuantity());
                }
                if (updatedRecipe.getInstructions() != null) {
                    existRecipe.setInstructions(updatedRecipe.getInstructions());
                }
                if (updatedRecipe.getCategoryId() != 0) {
                    existRecipe.setCategoryId(updatedRecipe.getCategoryId());
                }
                recipeDAOImpl.updateRecipe(existRecipe);
                String jsonResponse = objectMapper.writeValueAsString(existRecipe);
                String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
                output.write(response.getBytes());
            } else {
                String errorMessage = "Recipe not found for id: " + recipeId;
                String errorResponse = "HTTP/1.1 404 Not Found\r\nContent-Type: text/plain\r\nContent-Length: " + errorMessage.length() + "\r\n\r\n" + errorMessage;
                output.write(errorResponse.getBytes());
            }
        } catch (NumberFormatException e) {
            String errorMessage = "Invalid id format";
            String errorResponse = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/plain\r\nContent-Length: " + errorMessage.length() + "\r\n\r\n" + errorMessage;
            output.write(errorResponse.getBytes());
        }
    }
}
