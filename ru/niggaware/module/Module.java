package ru.niggaware.module;

public abstract class Module {
    
    private String name;
    private int keyCode;
    private Category category;
    private boolean enabled;
    
    public Module(String name, int keyCode, Category category) {
        this.name = name;
        this.keyCode = keyCode;
        this.category = category;
        this.enabled = false;
    }
    
    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }
    
    public void onEnable() {}
    public void onDisable() {}
    public void onUpdate() {}
    public void onRender() {}
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public int getKeyCode() {
        return keyCode;
    }
    
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public enum Category {
        COMBAT, MOVEMENT, PLAYER, RENDER, MISC
    }
}
