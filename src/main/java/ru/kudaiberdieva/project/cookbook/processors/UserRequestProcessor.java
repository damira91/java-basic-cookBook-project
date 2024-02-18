package ru.kudaiberdieva.project.cookbook.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kudaiberdieva.project.cookbook.DAO.UserDAOImpl;
import ru.kudaiberdieva.project.cookbook.entity.User;
import ru.kudaiberdieva.project.cookbook.http.HttpRequest;
import ru.kudaiberdieva.project.cookbook.http.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

public class UserRequestProcessor implements RequestProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserDAOImpl userDAOImpl = new UserDAOImpl();

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String email = request.getParameter("email");

        User existingUser = userDAOImpl.findUserByEmail(email);

        if (existingUser != null) {
            String responseMessage = "User with email " + email + " is already registered.";
            String response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: " + responseMessage.length() + "\r\n\r\n" + responseMessage;
            output.write(response.getBytes());
        } else {
            // Пользователь не найден, предлагаем зарегистрироваться
            // Здесь может быть логика для предложения пользователю зарегистрироваться, например, HTML форма или JSON сообщение с указанием действий
            String registrationMessage = "User with email " + email + " is not registered. Please register to continue.";
            String response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: " + registrationMessage.length() + "\r\n\r\n" + registrationMessage;
            output.write(response.getBytes());
        }
    }
}
