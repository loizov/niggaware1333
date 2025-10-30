package ru.niggaware.render.display.shape;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;
import ru.niggaware.common.InstanceAccess;

public class Round implements InstanceAccess {
	public static void drawRoundedRect(MatrixStack matrix, double x, double y, double width, double height, double radius, int color) {
	    Matrix4f matrix4f = matrix.getLast().getMatrix();
	    
	    int a = (color >> 24) & 0xFF;
	    int r = (color >> 16) & 0xFF;
	    int g = (color >> 8) & 0xFF;
	    int b = color & 0xFF;

	    // Преобразуем к диапазону 0..1 для альфа, если требуется
	    float af = a / 255.0F;
	    float rf = r / 255.0F;
	    float gf = g / 255.0F;
	    float bf = b / 255.0F;

	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

	    // Рисуем main прямоугольник со скруглениями при помощи нескольких квадов
	    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	    
	    // Прямоугольник без скруглений верх/низ по ширине
	    buffer.pos(matrix4f, (float) (x + radius), (float) y, 0).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) (x + width - radius), (float) y, 0).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) (x + width - radius), (float) (y + height), 0).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) (x + radius), (float) (y + height), 0).color(r, g, b, a).endVertex();

	    Tessellator.getInstance().draw();
	    
	    GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
	    int a = (color >> 24) & 0xFF;
	    int r = (color >> 16) & 0xFF;
	    int g = (color >> 8) & 0xFF;
	    int b = color & 0xFF;

	    float af = a / 255.0F;
	    float rf = r / 255.0F;
	    float gf = g / 255.0F;
	    float bf = b / 255.0F;

	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

	    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	    
	    buffer.pos((float) (x + radius), (float) y, 0).color(r, g, b, a).endVertex();
	    buffer.pos((float) (x + width - radius), (float) y, 0).color(r, g, b, a).endVertex();
	    buffer.pos((float) (x + width - radius), (float) (y + height), 0).color(r, g, b, a).endVertex();
	    buffer.pos((float) (x + radius), (float) (y + height), 0).color(r, g, b, a).endVertex();
	    
	    Tessellator.getInstance().draw();
	    
	    GL11.glDisable(GL11.GL_BLEND);
	}

}