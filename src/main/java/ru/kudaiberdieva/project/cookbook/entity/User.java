package ru.kudaiberdieva.project.cookbook.entity;

public class User {
    private String userId;
    private String name;
    private String password;
    private String email;
    private int token;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getToken() {
        return token;
    }

    public User(String userId, String name, String password, String email, int token) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", token=" + token +
                '}';
    }
}
