package ru.niggaware.module.combat;

import ru.niggaware.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;

public class AntiBot extends Module {
    
    public AntiBot() {
        super("AntiBot", GLFW.GLFW_KEY_UNKNOWN, Category.COMBAT);
    }
    
    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;
        
        // Удаляем невидимых сущностей (ботов)
        mc.level.players().removeIf(player -> 
            player != mc.player && player.isInvisible()
        );
    }
}
