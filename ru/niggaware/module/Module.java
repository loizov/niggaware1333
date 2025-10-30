package ru.niggaware.module;

import net.minecraft.client.Minecraft;

public class Module {
    protected final Minecraft mc = Minecraft.getInstance();
    
    private final String name;
    private final Category category;
    private boolean enabled;
    private int key;
    
    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
        this.enabled = false;
        this.key = 0;
    }
    
    public void toggle() {
        setEnabled(!enabled);
    }
    
    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        
        this.enabled = enabled;
        
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }
    
    public void onEnable() {}
    
    public void onDisable() {}
    
    public String getName() {
        return name;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public int getKey() {
        return key;
    }
    
    public void setKey(int key) {
        this.key = key;
    }
    
    public enum Category {
        COMBAT,
        MOVEMENT,
        RENDER,
        MISC
    }
}
