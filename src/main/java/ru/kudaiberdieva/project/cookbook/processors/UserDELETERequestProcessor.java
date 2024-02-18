package ru.kudaiberdieva.project.cookbook.processors;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kudaiberdieva.project.cookbook.DAO.UserDAOImpl;
import ru.kudaiberdieva.project.cookbook.entity.User;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

public class UserDELETERequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserDAOImpl userDAOImpl = new UserDAOImpl();

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String email = request.getParameter("email");
        if (email != null) {
            User user = userDAOImpl.findUserByEmail(email);
            if (user != null) {
                userDAOImpl.deleteUser(email);
                String jsonResponse = "User deleted successfully";
                String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
                output.write(response.getBytes());
            } else {
                String jsonResponse = "User not found";
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
