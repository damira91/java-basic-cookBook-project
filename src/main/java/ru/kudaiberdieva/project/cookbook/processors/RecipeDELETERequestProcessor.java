package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.kudaiberdieva.project.cookbook.DAO.RecipeDAOImpl;
import ru.kudaiberdieva.project.cookbook.entity.Recipe;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

public class RecipeDELETERequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RecipeDAOImpl recipeDAOImpl = new RecipeDAOImpl();
    private Gson gson;
    private Recipe recipe;

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String id = request.getParameter("id");
        if (id != null) {
            int recipeId = Integer.parseInt(id);
            Recipe recipe = recipeDAOImpl.getRecipeById(recipeId);
            if (recipe != null) {
                recipeDAOImpl.deleteRecipe(recipeId);
                String jsonResponse = "Recipe deleted successfully";
                String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
                output.write(response.getBytes());
            } else {
                String jsonResponse = "Recipe not found";
                String response = "HTTP/1.1 404 Not Found\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
                output.write(response.getBytes());
            }
        } else {
            String jsonResponse = "Invalid request";
            String response = "HTTP/1.1 400 Bad Request\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
            output.write(response.getBytes());
        }
    }
}
