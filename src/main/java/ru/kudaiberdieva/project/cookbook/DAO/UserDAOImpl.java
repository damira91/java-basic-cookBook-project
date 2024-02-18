package ru.kudaiberdieva.project.cookbook.DAO;

import ru.kudaiberdieva.project.cookbook.entity.User;
import ru.kudaiberdieva.project.cookbook.utils.DBconnection;

import java.sql.*;
import java.util.UUID;

public class UserDAOImpl implements UserDAO {

    public boolean isExist(String query, String parameter) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, parameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteUser(String email) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        String deleteUserQuery = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement deleteUser = connection.prepareStatement(deleteUserQuery)) {
            deleteUser.setString(1, email);
            connection.setAutoCommit(false);
            deleteUser.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    @Override
    public User findUserByEmail(String email) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        String selectUserQuery = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement selectUser = connection.prepareStatement(selectUserQuery)) {
            selectUser.setString(1, email);
            try (ResultSet resultSet = selectUser.executeQuery()) {
                if (resultSet.next()) {
                    String userId = resultSet.getString("user_id");
                    String username = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String userEmail = resultSet.getString("email");
                    int token = resultSet.getInt("token");
                    return new User(userId, username, password, userEmail, token);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    @Override
    public User register(User user) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        try {
            connection.setAutoCommit(false);
            String email = user.getEmail();
            String name = user.getName();
            int token = user.getToken();
            String password = user.getPassword();

            String insertUserQuery = "INSERT INTO users (user_id, name, password, email, token) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertUser = connection.prepareStatement(insertUserQuery)) {
                UUID userId = UUID.randomUUID();
                insertUser.setString(1, userId.toString());
                insertUser.setString(2, name);
                insertUser.setString(3, password);
                insertUser.setString(4, email);
                insertUser.setInt(5, token);
                insertUser.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public boolean isUsernameAlreadyExist(String username) {
        String query = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
        return isExist(query, username);
    }

    @Override
    public void update(User user) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        try {
            connection.setAutoCommit(false);
            String updatePasswordQuery = "UPDATE users SET password = ? ,name = ? WHERE email = ?";
            try (PreparedStatement updateUser = connection.prepareStatement(updatePasswordQuery)) {
                updateUser.setString(1, user.getPassword());
                updateUser.setString(2, user.getName());
                updateUser.setString(3, user.getEmail());
                updateUser.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }
}
