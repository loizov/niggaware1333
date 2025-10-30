package ru.niggaware.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    
    public static void drawRoundedRect(MatrixStack ms, float x, float y, float width, float height, float radius, int color) {
        drawRect(ms, x + radius, y, width - radius * 2, radius, color);
        drawRect(ms, x + radius, y + height - radius, width - radius * 2, radius, color);
        drawRect(ms, x, y + radius, width, height - radius * 2, color);
        
        drawRoundedCorner(ms, x + radius, y + radius, radius, 180, color);
        drawRoundedCorner(ms, x + width - radius, y + radius, radius, 270, color);
        drawRoundedCorner(ms, x + width - radius, y + height - radius, radius, 0, color);
        drawRoundedCorner(ms, x + radius, y + height - radius, radius, 90, color);
    }
    
    private static void drawRoundedCorner(MatrixStack ms, float x, float y, float radius, float startAngle, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0f;
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(red, green, blue, alpha);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        buffer.pos(x, y, 0).endVertex();
        
        for (int i = 0; i <= 90; i += 3) {
            double angle = Math.toRadians(startAngle + i);
            buffer.pos(x + Math.cos(angle) * radius, y + Math.sin(angle) * radius, 0).endVertex();
        }
        
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.color4f(1, 1, 1, 1);
    }
    
    public static void drawRect(MatrixStack ms, float x, float y, float width, float height, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0f;
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(red, green, blue, alpha);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        buffer.pos(x, y + height, 0).endVertex();
        buffer.pos(x + width, y + height, 0).endVertex();
        buffer.pos(x + width, y, 0).endVertex();
        buffer.pos(x, y, 0).endVertex();
        tessellator.draw();
        
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.color4f(1, 1, 1, 1);
    }
    
    public static void drawGradientRect(MatrixStack ms, float x, float y, float width, float height, int colorStart, int colorEnd) {
        float alphaStart = (colorStart >> 24 & 0xFF) / 255.0f;
        float redStart = (colorStart >> 16 & 0xFF) / 255.0f;
        float greenStart = (colorStart >> 8 & 0xFF) / 255.0f;
        float blueStart = (colorStart & 0xFF) / 255.0f;
        
        float alphaEnd = (colorEnd >> 24 & 0xFF) / 255.0f;
        float redEnd = (colorEnd >> 16 & 0xFF) / 255.0f;
        float greenEnd = (colorEnd >> 8 & 0xFF) / 255.0f;
        float blueEnd = (colorEnd & 0xFF) / 255.0f;
        
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y + height, 0).color(redEnd, greenEnd, blueEnd, alphaEnd).endVertex();
        buffer.pos(x + width, y + height, 0).color(redEnd, greenEnd, blueEnd, alphaEnd).endVertex();
        buffer.pos(x + width, y, 0).color(redStart, greenStart, blueStart, alphaStart).endVertex();
        buffer.pos(x, y, 0).color(redStart, greenStart, blueStart, alphaStart).endVertex();
        tessellator.draw();
        
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
    
    public static void drawBorder(MatrixStack ms, float x, float y, float width, float height, float thickness, int color) {
        drawRect(ms, x, y, width, thickness, color);
        drawRect(ms, x, y + height - thickness, width, thickness, color);
        drawRect(ms, x, y, thickness, height, color);
        drawRect(ms, x + width - thickness, y, thickness, height, color);
    }
    
    public static void drawGlow(MatrixStack ms, float x, float y, float width, float height, int glowSize, int color) {
        int alpha = (color >> 24 & 0xFF);
        int rgb = color & 0xFFFFFF;
        
        for (int i = 0; i < glowSize; i++) {
            int glowAlpha = (int) (alpha * (1 - i / (float) glowSize) * 0.3f);
            int glowColor = (glowAlpha << 24) | rgb;
            
            drawRect(ms, x - i, y - i, width + i * 2, 1, glowColor);
            drawRect(ms, x - i, y + height + i - 1, width + i * 2, 1, glowColor);
            drawRect(ms, x - i, y - i, 1, height + i * 2, glowColor);
            drawRect(ms, x + width + i - 1, y - i, 1, height + i * 2, glowColor);
        }
    }
    
    public static void enableScissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        double scale = mc.getMainWindow().getGuiScaleFactor();
        RenderSystem.enableScissor(
            (int) (x * scale),
            (int) (mc.getMainWindow().getHeight() - (y + height) * scale),
            (int) (width * scale),
            (int) (height * scale)
        );
    }
    
    public static void disableScissor() {
        RenderSystem.disableScissor();
    }
    
    // Дополнительные утилиты
    public static void drawCircle(MatrixStack ms, float x, float y, float radius, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0f;
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(red, green, blue, alpha);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        buffer.pos(x, y, 0).endVertex();
        
        for (int i = 0; i <= 360; i += 5) {
            double angle = Math.toRadians(i);
            buffer.pos(x + Math.cos(angle) * radius, y + Math.sin(angle) * radius, 0).endVertex();
        }
        
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.color4f(1, 1, 1, 1);
    }
    
    public static void drawLine(MatrixStack ms, float x1, float y1, float x2, float y2, float thickness, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0f;
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(red, green, blue, alpha);
        RenderSystem.lineWidth(thickness);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        buffer.pos(x1, y1, 0).endVertex();
        buffer.pos(x2, y2, 0).endVertex();
        tessellator.draw();
        
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.lineWidth(1);
    }
}
