package ru.niggaware.render.display.shape;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class Blur {

    public static void drawBlur(MatrixStack matrix, double x, double y, double width, double height, int color, float blurRadius) {
        Matrix4f matrix4f = matrix.getLast().getMatrix();

        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        // Верхний левый угол
        buffer.pos(matrix4f, (float) x, (float) (y + height), 0).color(r, g, b, a).endVertex();
        // Верхний правый
        buffer.pos(matrix4f, (float) (x + width), (float) (y + height), 0).color(r, g, b, a).endVertex();
        // Нижний правый
        buffer.pos(matrix4f, (float) (x + width), (float) y, 0).color(r, g, b, a).endVertex();
        // Нижний левый
        buffer.pos(matrix4f, (float) x, (float) y, 0).color(r, g, b, a).endVertex();

        Tessellator.getInstance().draw();

        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawBlur(float x, float y, float width, float height, int color, float blurRadius) {
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        buffer.pos((double) x, (double) (y + height), 0).color(r, g, b, a).endVertex();
        buffer.pos((double) (x + width), (double) (y + height), 0).color(r, g, b, a).endVertex();
        buffer.pos((double) (x + width), (double) y, 0).color(r, g, b, a).endVertex();
        buffer.pos((double) x, (double) y, 0).color(r, g, b, a).endVertex();

        Tessellator.getInstance().draw();

        GL11.glDisable(GL11.GL_BLEND);
    }
}
