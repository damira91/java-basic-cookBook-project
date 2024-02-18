package ru.kudaiberdieva.project.cookbook.DAO;

import ru.kudaiberdieva.project.cookbook.entity.User;


public interface UserDAO {
    User register(User user);

    boolean isExist(String query, String parameter);

    void update(User user);

    boolean isUsernameAlreadyExist(String username);

    void deleteUser(String email);

    User findUserByEmail(String email);

}
