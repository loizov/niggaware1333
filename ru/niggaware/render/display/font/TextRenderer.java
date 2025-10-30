package ru.niggaware.render.display.font;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;
import ru.niggaware.common.InstanceAccess;

public class TextRenderer implements InstanceAccess {
    
    public static void drawText(MatrixStack matrix, String text, float x, float y, int color) {
        mc.fontRenderer.drawString(matrix, text, (int) x, (int) y, color);
    }
    
    public static void drawTextWithShadow(MatrixStack matrix, String text, float x, float y, int color) {
        mc.fontRenderer.drawStringWithShadow(matrix, text, (int) x, (int) y, color);
    }
    
    public static void drawCenteredText(MatrixStack matrix, String text, float x, float y, int color) {
        int textWidth = mc.fontRenderer.getStringWidth(text);
        mc.fontRenderer.drawString(matrix, text, (int) (x - textWidth / 2.0f), (int) y, color);
    }
    
    public static void drawCenteredTextWithShadow(MatrixStack matrix, String text, float x, float y, int color) {
        int textWidth = mc.fontRenderer.getStringWidth(text);
        mc.fontRenderer.drawStringWithShadow(matrix, text, (int) (x - textWidth / 2.0f), (int) y, color);
    }
    
    public static int getTextWidth(String text) {
        return mc.fontRenderer.getStringWidth(text);
    }
    
    public static int getTextHeight() {
        return mc.fontRenderer.FONT_HEIGHT;
    }
}
