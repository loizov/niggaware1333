package ru.niggaware.module.movement;

import ru.niggaware.NiggaWare;
import ru.niggaware.EventTarget;
import ru.niggaware.module.Module;
import ru.niggaware.UpdateEvent;
import org.lwjgl.glfw.GLFW;

public class Fly extends Module {
    private float speed = 0.5F;
    
    public Fly() {
        super("Fly", Category.MOVEMENT);
        setKey(GLFW.GLFW_KEY_F);
    }
    
    @Override
    public void onEnable() {
        NiggaWare.INSTANCE.eventManager.register(this);
        
        if (mc.player != null) {
            mc.player.abilities.allowFlying = true;
            mc.player.abilities.isFlying = true;
        }
    }
    
    @Override
    public void onDisable() {
        NiggaWare.INSTANCE.eventManager.unregister(this);
        
        if (mc.player != null) {
            mc.player.abilities.allowFlying = false;
            mc.player.abilities.isFlying = false;
        }
    }
    
    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (mc.player == null) {
            return;
        }
        
        mc.player.abilities.setFlySpeed(speed / 10F);
        mc.player.fallDistance = 0;
    }
}
