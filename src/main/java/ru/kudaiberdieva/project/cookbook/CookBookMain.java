package ru.kudaiberdieva.project.cookbook;

import ru.kudaiberdieva.project.cookbook.http.Server;

public class CookBookMain {
    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt((String) System.getProperties().getOrDefault("port", "8189")));
        server.start();
    }
}
