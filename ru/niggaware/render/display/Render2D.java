package ru.niggaware.render.display;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;
import ru.niggaware.common.InstanceAccess;

public class Render2D implements InstanceAccess {
	// Рисование прямоугольника с заданным цветом ARGB (int color)
	public static void drawRect(MatrixStack matrix, double x, double y, double width, double height, int color) {
	    Matrix4f matrix4f = matrix.getLast().getMatrix();

	    // извлечение компонент цвета (0-255)
	    int a = (color >> 24) & 0xFF;
	    int r = (color >> 16) & 0xFF;
	    int g = (color >> 8) & 0xFF;
	    int b = color & 0xFF;

	    // Применяем прозрачность
	    float af = a / 255.0F;
	    float rf = r / 255.0F;
	    float gf = g / 255.0F;
	    float bf = b / 255.0F;

	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

	    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	    buffer.pos(matrix4f, (float) x, (float) (y + height), 0).color((int) r, (int) g, (int) b, a).endVertex();
	    buffer.pos(matrix4f, (float) (x + width), (float) (y + height), 0).color((int) r, (int) g, (int) b, a).endVertex();
	    buffer.pos(matrix4f, (float) (x + width), (float) y, 0).color((int) r, (int) g, (int) b, a).endVertex();
	    buffer.pos(matrix4f, (float) x, (float) y, 0).color((int) r, (int) g, (int) b, a).endVertex();
	    Tessellator.getInstance().draw();

	    GL11.glDisable(GL11.GL_BLEND);
	}

	// Перегрузка без MatrixStack (оригинальный статический вызов)
	public static void drawRect(double x, double y, double width, double height, int color) {
	    float r = (color >> 16 & 255) / 255.0F;
	    float g = (color >> 8 & 255) / 255.0F;
	    float b = (color & 255) / 255.0F;
	    float a = (color >> 24 & 255) / 255.0F;

	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

	    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	    buffer.pos((float) x, (float) (y + height), 0).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
	    buffer.pos((float) (x + width), (float) (y + height), 0).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
	    buffer.pos((float) (x + width), (float) y, 0).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
	    buffer.pos((float) x, (float) y, 0).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
	    Tessellator.getInstance().draw();

	    GL11.glDisable(GL11.GL_BLEND);
	}

	// Пример градиентного заливки (может быть удалён, если не требуется)
	public static void drawGradientRect(MatrixStack matrix, double x, double y, double width, double height, int color1, int color2) {
	    Matrix4f matrix4f = matrix.getLast().getMatrix();

	    float r1 = (color1 >> 16 & 255) / 255.0F;
	    float g1 = (color1 >> 8 & 255) / 255.0F;
	    float b1 = (color1 & 255) / 255.0F;
	    float a1 = (color1 >> 24 & 255) / 255.0F;

	    float r2 = (color2 >> 16 & 255) / 255.0F;
	    float g2 = (color2 >> 8 & 255) / 255.0F;
	    float b2 = (color2 & 255) / 255.0F;
	    float a2 = (color2 >> 24 & 255) / 255.0F;

	    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
	    buffer.pos(matrix4f, (float) x, (float) (y + height), 0).color((int) (r1 * 255), (int) (g1 * 255), (int) (b1 * 255), (int) (a1 * 255)).endVertex();
	    buffer.pos(matrix4f, (float) (x + width), (float) (y + height), 0).color((int) (r2 * 255), (int) (g2 * 255), (int) (b2 * 255), (int) (a2 * 255)).endVertex();
	    buffer.pos(matrix4f, (float) (x + width), (float) y, 0).color((int) (r2 * 255), (int) (g2 * 255), (int) (b2 * 255), (int) (a2 * 255)).endVertex();
	    buffer.pos(matrix4f, (float) x, (float) y, 0).color((int) (r1 * 255), (int) (g1 * 255), (int) (b1 * 255), (int) (a1 * 255)).endVertex();
	    Tessellator.getInstance().draw();
	}

}