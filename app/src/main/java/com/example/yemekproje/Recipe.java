package com.example.yemekproje;

import com.google.firebase.firestore.PropertyName;

public class Recipe {
    private String name;
    private String details;
    private int imageResource;
    private String imageUrl; 
    private String drawableName; 
    private boolean isFavorite;
    private String ingredients;
    private String instructions;
    private String category;
    private String userId;
    private int favoriteCount;
    private int commentCount;
    private String videoUrl;
    private String mainIngredient;
    private String missingIngredients;

    public Recipe() {}

    // Statik tarifler için güncellenmiş constructor
    public Recipe(String name, String details, String drawableName, String ingredients, String instructions) {
        this.name = name;
        this.details = details;
        this.drawableName = drawableName;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Getter ve Setterlar
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public int getImageResource() { return imageResource; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getDrawableName() { return drawableName; }
    public void setDrawableName(String drawableName) { this.drawableName = drawableName; }
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public int getFavoriteCount() { return favoriteCount; }
    public void setFavoriteCount(int favoriteCount) { this.favoriteCount = favoriteCount; }
    public int getCommentCount() { return commentCount; }
    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }
    public String getMainIngredient() { return mainIngredient; }
    public void setMainIngredient(String mainIngredient) { this.mainIngredient = mainIngredient; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public String getMissingIngredients() { return missingIngredients; }
    public void setMissingIngredients(String missingIngredients) { this.missingIngredients = missingIngredients; }

    @PropertyName("favorite")
    public boolean isFavorite() { return isFavorite; }
    @PropertyName("favorite")
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}