package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kudaiberdieva.project.cookbook.DAO.RecipeDAOImpl;
import ru.kudaiberdieva.project.cookbook.entity.Recipe;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

public class RecipeRequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RecipeDAOImpl recipeDAOImpl = new RecipeDAOImpl();

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String title = request.getParameter("name");
        Recipe recipe = recipeDAOImpl.searchRecipe(title);
        String jsonResponse = objectMapper.writeValueAsString(recipe);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
        output.write(response.getBytes());

    }
}
