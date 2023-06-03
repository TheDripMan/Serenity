package dev.serenity.module;

public enum Category {
    COMBAT("Combat"),
    PLAYER("Player"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    WORLD("World");

    private final String name;
    public static Category selectedCategory = COMBAT;
    public float scrollHeight = 0F;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
