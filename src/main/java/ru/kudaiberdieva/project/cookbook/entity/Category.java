package ru.kudaiberdieva.project.cookbook.entity;

import java.io.Serializable;

public class Category implements Serializable {

    private int categoryId;
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + categoryId +
                ", title='" + categoryName + '\'' +
                '}';
    }

}
