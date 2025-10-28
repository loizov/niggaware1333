package ru.niggaware.module.movement;

import ru.niggaware.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Fly extends Module {
    
    private double flySpeed = 0.5;
    
    public Fly() {
        super("Fly", GLFW.GLFW_KEY_F, Category.MOVEMENT);
    }
    
    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.abilities.flying = true;
            mc.player.abilities.mayfly = true;
            mc.player.abilities.setFlyingSpeed((float) flySpeed / 10);
        }
    }
    
    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && !mc.player.isCreative()) {
            mc.player.abilities.flying = false;
            mc.player.abilities.mayfly = false;
        }
    }
}
