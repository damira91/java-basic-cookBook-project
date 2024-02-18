package ru.kudaiberdieva.project.cookbook.processors;

import com.google.gson.Gson;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kudaiberdieva.project.cookbook.DAO.CategoryDAOImpl;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.entity.Category;

import java.io.IOException;
import java.io.OutputStream;

public class CategoryPUTRequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl();
    private Gson gson;

    public CategoryPUTRequestProcessor() {
        gson = new Gson();
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String id = request.getParameter("id");
        int categoryId = Integer.parseInt(id);
        Category existCategory = categoryDAOImpl.getCategoryById(categoryId);
        if (existCategory != null) {
            String requestBody = request.getBody();
            Category updatedCategory = gson.fromJson(requestBody, Category.class);
            if (updatedCategory.getCategoryName() != null) {
                existCategory.setCategoryName(updatedCategory.getCategoryName());
            }
            categoryDAOImpl.updateCategory(existCategory);
            String jsonResponse = objectMapper.writeValueAsString(existCategory);
            String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
            output.write(response.getBytes());
        } else {
            String errorMessage = "Category not found for id: " + categoryId;
            String errorResponse = "HTTP/1.1 404 Not Found\r\nContent-Type: text/plain\r\nContent-Length: " + errorMessage.length() + "\r\n\r\n" + errorMessage;
            output.write(errorResponse.getBytes());
        }
    }
}
