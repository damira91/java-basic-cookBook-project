package ru.kudaiberdieva.project.cookbook.DAO;


import ru.kudaiberdieva.project.cookbook.entity.Category;
import ru.kudaiberdieva.project.cookbook.utils.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public Category addCategory(Category category) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();

        String categoryName = category.getCategoryName();
        String categoryInsertRequest = "INSERT INTO category (\"category_name\") VALUES (?)";
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement insertCategory = connection.prepareStatement(categoryInsertRequest, Statement.RETURN_GENERATED_KEYS)) {
                insertCategory.setString(1, String.valueOf(categoryName));
                insertCategory.executeUpdate();

                try (ResultSet genKeys = insertCategory.getGeneratedKeys()) {
                    if (genKeys.next()) {
                        int categoryId = genKeys.getInt(1);
                        category.setCategoryId(categoryId);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.setAutoCommit(false);
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
        return category;
    }


    @Override
    public void updateCategory(Category category) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        try {
            connection.setAutoCommit(false);
            String categoryUpdateRequest = "UPDATE category SET category_name = ? WHERE category_id = ?";
            try (PreparedStatement updateCategory = connection.prepareStatement(categoryUpdateRequest)) {
                updateCategory.setString(1, category.getCategoryName());
                updateCategory.setInt(2, category.getCategoryId());
                int rowsAffected = updateCategory.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Категория успешно обновлена.");
                } else {
                    System.out.println("Не удалось обновить категорию.");
                }
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

    @Override
    public void deleteCategory(int categoryId) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        String categoryDelete = "DELETE FROM category WHERE category_id = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement deleteCategory = connection.prepareStatement(categoryDelete)) {
                deleteCategory.setInt(1, categoryId);
                deleteCategory.executeUpdate();
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

    @Override
    public List<Category> getAllCategory() {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        String getCategoriesQuery = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement getCategories = connection.prepareStatement(getCategoriesQuery);
             ResultSet resultSet = getCategories.executeQuery()) {
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category_id");
                String categoryName = resultSet.getString("category_name");
                Category category = new Category(categoryId, categoryName);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getCategoryById(int id) {
        DBconnection dBconnection = new DBconnection();
        Connection connection = dBconnection.connection();
        Category category = null;
        try {
            String categoryByIdQuery = "SELECT category_name FROM category WHERE category_id = ?";
            try (PreparedStatement getCategory = connection.prepareStatement(categoryByIdQuery)) {
                getCategory.setInt(1, id);
                try (ResultSet resultSet = getCategory.executeQuery()) {
                    if (resultSet.next()) {
                        String categoryName = resultSet.getString("category_name");
                        category = new Category(id, categoryName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
        return category;
    }
}
