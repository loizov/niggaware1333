package ru.niggaware.module.combat;

import ru.niggaware.NiggaWare;
import ru.niggaware.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashSet;
import java.util.Set;

public class AntiBot extends Module {
    private final Set<Entity> bots = new HashSet<>();
    
    public AntiBot() {
        super("AntiBot", Category.COMBAT);
    }
    
    @Override
    public void onEnable() {
        NiggaWare.INSTANCE.eventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        NiggaWare.INSTANCE.eventManager.unregister(this);
        bots.clear();
    }
    
    public boolean isBot(Entity entity) {
        if (!isEnabled()) {
            return false;
        }
        
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }
        
        PlayerEntity player = (PlayerEntity) entity;
        
        if (player.isInvisible()) {
            bots.add(entity);
            return true;
        }
        
        if (player.getGameProfile().getId() == null) {
            bots.add(entity);
            return true;
        }
        
        return bots.contains(entity);
    }
}
