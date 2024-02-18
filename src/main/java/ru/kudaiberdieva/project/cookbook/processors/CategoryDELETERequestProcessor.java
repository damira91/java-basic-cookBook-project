package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kudaiberdieva.project.cookbook.DAO.CategoryDAOImpl;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;
import ru.kudaiberdieva.project.cookbook.entity.Category;

import java.io.IOException;
import java.io.OutputStream;

public class CategoryDELETERequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl();

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String id = request.getParameter("id");
        if (id != null) {
            int categoryId = Integer.parseInt(id);
            Category category = categoryDAOImpl.getCategoryById(categoryId);
            if (category != null) {
                categoryDAOImpl.deleteCategory(categoryId);
                String jsonResponse = "Category deleted successfully";
                String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
                output.write(response.getBytes());
            } else {
                String jsonResponse = "Category not found";
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
