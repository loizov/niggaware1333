package ru.niggaware.module.combat;

import ru.niggaware.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class KillAura extends Module {
    
    private double range = 4.2;
    
    public KillAura() {
        super("KillAura", GLFW.GLFW_KEY_R, Category.COMBAT);
    }
    
    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        
        for (LivingEntity entity : mc.level.getEntitiesOfClass(LivingEntity.class, 
            mc.player.getBoundingBox().inflate(range))) {
            
            if (entity != mc.player && entity.isAlive()) {
                // Поворот к цели
                float[] rotations = getRotations(entity);
                mc.player.xRot = rotations[0];
                mc.player.yRot = rotations[1];
                
                // Атака
                mc.gameMode.attack(mc.player, entity);
                mc.player.swing(mc.player.getUsedItemHand());
                break;
            }
        }
    }
    
    private float[] getRotations(LivingEntity entity) {
        Minecraft mc = Minecraft.getInstance();
        double x = entity.getX() - mc.player.getX();
        double y = entity.getY() + entity.getEyeHeight() - mc.player.getEyeY();
        double z = entity.getZ() - mc.player.getZ();
        
        double dist = Math.sqrt(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float) (-(Math.atan2(y, dist) * 180.0 / Math.PI));
        
        return new float[]{pitch, yaw};
    }
}
