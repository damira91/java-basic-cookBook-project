package ru.kudaiberdieva.project.cookbook.entity;


public class Recipe {
    private int recipeId;
    private String recipeName;
    private String ingredients;
    private String instructions;
    private int quantity;
    private int categoryId;
    private String categoryName;
    private Category category;

    public Recipe(int recipeId, String recipeName, String ingredients, String instructions, int quantity, Category category) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.quantity = quantity;
        this.category = category;
    }

    public Recipe(String recipeName, String ingredients, String instructions, int quantity, int categoryId) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCategoryName(Category category) {
        this.categoryName = category.getCategoryName();
    }

    public String getCategoryName() {
        if (category != null) {
            return category.getCategoryName();
        } else {
            return null;
        }
    }




}
