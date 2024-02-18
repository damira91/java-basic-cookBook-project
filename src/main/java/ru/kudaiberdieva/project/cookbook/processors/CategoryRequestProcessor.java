package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kudaiberdieva.project.cookbook.DAO.CategoryDAOImpl;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;
import ru.kudaiberdieva.project.cookbook.entity.Category;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CategoryRequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl();

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String id;
        id = request.getParameter("id");
        if (id != null) {
            Category category = categoryDAOImpl.getCategoryById(Integer.parseInt(request.getParameter("id")));
            String jsonResponse = objectMapper.writeValueAsString(category);
            String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
            output.write(response.getBytes());
        } else {
            List<Category> categories = categoryDAOImpl.getAllCategory();
            String jsonResponse = objectMapper.writeValueAsString(categories);
            String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
            output.write(response.getBytes());
        }
    }
}
