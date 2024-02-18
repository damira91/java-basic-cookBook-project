package ru.kudaiberdieva.project.cookbook.http;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private Dispatcher dispatcher;

    public Server(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новое подключение: " + clientSocket);
                handleClient(clientSocket); // Обрабатываем запрос клиента в н
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        new Thread(() -> {
            try (InputStream in = clientSocket.getInputStream();
                 OutputStream out = clientSocket.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int n = in.read(buffer);
                if (n != -1) {
                    String rawRequest = new String(buffer, 0, n);
                    HttpRequest httpRequest = new HttpRequest(rawRequest);
                    dispatcher.execute(httpRequest, out);
                } else {
                    System.out.println("No data received from client.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
