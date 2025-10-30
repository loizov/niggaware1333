package ru.niggaware.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FontRenderer {
    private Font font;
    private final CharData[] charData = new CharData[256];
    private int textureID;
    private int imgSize = 1024; // Увеличили для лучшего качества
    
    public FontRenderer(int size) {
        this("Arial", size);
    }
    
    public FontRenderer(String fontName, int size) {
        this(new Font(fontName, Font.PLAIN, size));
    }
    
    public FontRenderer(Font font) {
        this.font = font;
        setupTexture();
    }
    
    private void setupTexture() {
        BufferedImage img = generateFontImage();
        textureID = loadTexture(img);
    }
    
    private BufferedImage generateFontImage() {
        BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        
        g.setFont(font);
        
        // Полная очистка
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, imgSize, imgSize);
        
        g.setComposite(AlphaComposite.SrcOver);
        g.setColor(Color.WHITE);
        
        // Настройки для четкого рендеринга
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        FontMetrics fontMetrics = g.getFontMetrics();
        int charHeight = 0;
        int positionX = 0;
        int positionY = 1;
        
        for (int i = 0; i < charData.length; i++) {
            char ch = (char) i;
            Rectangle2D bounds = fontMetrics.getStringBounds(String.valueOf(ch), g);
            
            int width = (int) bounds.getWidth() + 6;
            int height = (int) bounds.getHeight() + 3;
            
            if (positionX + width >= imgSize) {
                positionX = 0;
                positionY += charHeight;
                charHeight = 0;
            }
            
            if (height > charHeight) {
                charHeight = height;
            }
            
            charData[i] = new CharData();
            charData[i].width = width;
            charData[i].height = height;
            charData[i].storedX = positionX;
            charData[i].storedY = positionY;
            
            g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
            
            positionX += width;
        }
        
        g.dispose();
        return bufferedImage;
    }
    
    private int loadTexture(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocateDirect(pixels.length * 4);
        
        for (int pixel : pixels) {
            buffer.put((byte) ((pixel >> 16) & 0xFF));
            buffer.put((byte) ((pixel >> 8) & 0xFF));
            buffer.put((byte) (pixel & 0xFF));
            buffer.put((byte) ((pixel >> 24) & 0xFF));
        }
        
        buffer.flip();
        
        int textureID = GlStateManager.genTexture();
        GlStateManager.bindTexture(textureID);
        
        // Настройки для четкого текста
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.getWidth(), image.getHeight(), 
                         0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        
        return textureID;
    }
    
    public void drawString(MatrixStack matrixStack, String text, float x, float y, int color) {
        if (text == null || text.isEmpty()) {
            return;
        }
        
        float alpha = (color >> 24 & 0xFF) / 255.0f;
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableTexture();
        RenderSystem.color4f(red, green, blue, alpha);
        
        GlStateManager.bindTexture(textureID);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        
        float startX = x;
        
        for (char ch : text.toCharArray()) {
            if (ch < charData.length && ch >= 0) {
                CharData data = charData[ch];
                
                float width = data.width;
                float height = data.height;
                
                float u1 = data.storedX / (float) imgSize;
                float v1 = data.storedY / (float) imgSize;
                float u2 = (data.storedX + width) / (float) imgSize;
                float v2 = (data.storedY + height) / (float) imgSize;
                
                buffer.pos(startX, y + height, 0).tex(u1, v2).endVertex();
                buffer.pos(startX + width, y + height, 0).tex(u2, v2).endVertex();
                buffer.pos(startX + width, y, 0).tex(u2, v1).endVertex();
                buffer.pos(startX, y, 0).tex(u1, v1).endVertex();
                
                startX += width - 6;
            }
        }
        
        tessellator.draw();
        
        RenderSystem.disableBlend();
        RenderSystem.color4f(1, 1, 1, 1);
    }
    
    public void drawCenteredString(MatrixStack matrixStack, String text, float x, float y, int color) {
        drawString(matrixStack, text, x - getStringWidth(text) / 2f, y, color);
    }
    
    public int getStringWidth(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        
        int width = 0;
        for (char ch : text.toCharArray()) {
            if (ch < charData.length && ch >= 0) {
                width += charData[ch].width - 6;
            }
        }
        return width;
    }
    
    public int getHeight() {
        return font.getSize();
    }
    
    private static class CharData {
        public int width;
        public int height;
        public int storedX;
        public int storedY;
    }
}
	