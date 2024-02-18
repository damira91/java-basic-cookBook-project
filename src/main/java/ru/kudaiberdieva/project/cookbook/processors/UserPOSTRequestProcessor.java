package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.kudaiberdieva.project.cookbook.DAO.UserDAOImpl;
import ru.kudaiberdieva.project.cookbook.entity.User;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

public class UserPOSTRequestProcessor implements RequestProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserDAOImpl userDAOImpl = new UserDAOImpl();
    private Gson gson;

    public UserPOSTRequestProcessor() {
        gson = new Gson();
    }

    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String requestBody = request.getBody();
        User user = gson.fromJson(requestBody, User.class);
        User registeredUser = userDAOImpl.register(user);
        String jsonResponse = objectMapper.writeValueAsString(registeredUser);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: " + jsonResponse.length() + "\r\n\r\n" + jsonResponse;
        output.write(response.getBytes());
    }
}
