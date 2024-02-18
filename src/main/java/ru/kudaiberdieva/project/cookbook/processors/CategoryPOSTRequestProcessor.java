package ru.kudaiberdieva.project.cookbook.processors;

import com.google.gson.Gson;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kudaiberdieva.project.cookbook.DAO.CategoryDAOImpl;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.entity.Category;

import java.io.IOException;
import java.io.OutputStream;

public class CategoryPOSTRequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl();
    private Gson gson;

    public CategoryPOSTRequestProcessor() {
        gson = new Gson();
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String requestBody = request.getBody();
        Category category = gson.fromJson(requestBody, Category.class);
        String categoryName = category.getCategoryName();
        Category newCategory = new Category(categoryName);
        newCategory.setCategoryName(categoryName);
        Category addedCategory = categoryDAOImpl.addCategory(newCategory);
        String jsonResponse = objectMapper.writeValueAsString(addedCategory);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
        output.write(response.getBytes());
    }
}
