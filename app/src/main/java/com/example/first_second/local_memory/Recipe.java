package com.example.first_second.local_memory;

public class Recipe {
    /**
     * ID, welche von der DB beim Hinzufügen vergeben wurde.
     */
    private String id;
    private String recipeName;
    private String ingredients;
    private String directions;
    public Recipe(String recipeName, String ingredients, String directions){
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public String getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public void setId(String id) {
        this.id = id;
    }

}
