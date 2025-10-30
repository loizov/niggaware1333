package ru.niggaware.module.combat;

import ru.niggaware.NiggaWare;
import ru.niggaware.EventTarget;
import ru.niggaware.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class KillAura extends Module {
    private float range = 4.2F;
    private int delay = 100;
    private long lastAttack = 0;
    
    public KillAura() {
        super("KillAura", Category.COMBAT);
        setKey(GLFW.GLFW_KEY_R);
    }
    
    @Override
    public void onEnable() {
        NiggaWare.INSTANCE.eventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        NiggaWare.INSTANCE.eventManager.unregister(this);
    }
    
    @EventTarget
    public void onUpdate(Object event) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        
        if (System.currentTimeMillis() - lastAttack < delay) {
            return;
        }
        
        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof LivingEntity && entity != mc.player) {
                double distance = mc.player.getDistance(entity);
                if (distance <= range) {
                    if (entity instanceof PlayerEntity) {
                        mc.playerController.attackEntity(mc.player, entity);
                        mc.player.swing(Hand.MAIN_HAND, true);
                        lastAttack = System.currentTimeMillis();
                        break;
                    }
                }
            }
        }
    }
}
