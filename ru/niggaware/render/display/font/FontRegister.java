package ru.niggaware.render.display.font;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.niggaware.common.InstanceAccess;

public class FontRegister implements InstanceAccess {
    
    public static FontRenderer registerFont(ResourceLocation fontLocation) {
        try {
            // In a real implementation, you would load a custom font here
            // For now, we'll just return the default font renderer
            return mc.fontRenderer;
        } catch (Exception e) {
            e.printStackTrace();
            return mc.fontRenderer;
        }
    }
    
    public static FontRenderer getDefaultFont() {
        return mc.fontRenderer;
    }
}
