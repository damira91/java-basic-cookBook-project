package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.kudaiberdieva.project.cookbook.DAO.UserDAOImpl;
import ru.kudaiberdieva.project.cookbook.entity.User;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

public class UserPUTRequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserDAOImpl userDAOImpl = new UserDAOImpl();
    private Gson gson;

    public UserPUTRequestProcessor() {
        gson = new Gson();
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String email = request.getParameter("email");
        User existUser = userDAOImpl.findUserByEmail(email);
        if (existUser != null) {
            String requestBody = request.getBody();
            User updatedUser = gson.fromJson(requestBody, User.class);
            if (updatedUser.getPassword() != null) {
                existUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getName() != null) {
                existUser.setName(updatedUser.getName());
            }
            userDAOImpl.update(existUser);
            String jsonResponse = objectMapper.writeValueAsString(existUser);
            String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
            output.write(response.getBytes());
        } else {
            String errorMessage = "User with " + email + " is not found!";
            String errorResponse = "HTTP/1.1 404 Not Found\r\nContent-Type: text/plain\r\nContent-Length: " + errorMessage.length() + "\r\n\r\n" + errorMessage;
            output.write(errorResponse.getBytes());
        }

    }
}
