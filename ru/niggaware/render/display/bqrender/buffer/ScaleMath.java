package ru.niggaware.render.display.bqrender.buffer;

import net.minecraft.client.Minecraft;
import ru.niggaware.common.InstanceAccess;

public class ScaleMath implements InstanceAccess {
    
    public static double scale(double value, double scale) {
        return value * scale;
    }
    
    public static double unscale(double value, double scale) {
        return value / scale;
    }
    
    public static int scaleToInt(double value, double scale) {
        return (int) Math.round(value * scale);
    }
    
    public static double scaleFromInt(int value, double scale) {
        return value / scale;
    }
    
    public static double getGuiScale() {
        return mc.getMainWindow().getGuiScaleFactor();
    }
    
    public static double getScaledWidth() {
        return mc.getMainWindow().getScaledWidth();
    }
    
    public static double getScaledHeight() {
        return mc.getMainWindow().getScaledHeight();
    }
}
