package ru.niggaware.render.world;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;
import ru.niggaware.common.InstanceAccess;

public class Render3D implements InstanceAccess {
	// Рисование прямой между двумя точками с указанным цветом ARGB
	public static void drawLine(MatrixStack matrix, Vector3d start, Vector3d end, int color) {
	    Matrix4f matrix4f = matrix.getLast().getMatrix();

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
	    GL11.glDisable(GL11.GL_DEPTH_TEST);

	    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	    buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
	    // Добавление вершин через существующий API: задаём позицию и цвет по вершине
	    buffer.pos(matrix4f, (float) start.x, (float) start.y, (float) start.z).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) end.x, (float) end.y, (float) end.z).color(r, g, b, a).endVertex();
	    Tessellator.getInstance().draw();

	    GL11.glEnable(GL11.GL_DEPTH_TEST);
	    GL11.glDisable(GL11.GL_BLEND);
	}

	// Рисование куба-оболочки (ось линейного каркаса)
	public static void drawBox(MatrixStack matrix, Vector3d pos1, Vector3d pos2, int color) {
	    Matrix4f matrix4f = matrix.getLast().getMatrix();

	    int a = (color >> 24) & 0xFF;
	    int r = (color >> 16) & 0xFF;
	    int g = (color >> 8) & 0xFF;
	    int b = color & 0xFF;

	    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    GL11.glDisable(GL11.GL_DEPTH_TEST);

	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

	    // Нижняя грань
	    buffer.pos(matrix4f, (float) pos1.x, (float) pos1.y, (float) pos1.z).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) pos2.x, (float) pos1.y, (float) pos1.z).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) pos2.x, (float) pos1.y, (float) pos2.z).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) pos1.x, (float) pos1.y, (float) pos2.z).color(r, g, b, a).endVertex();

	    // Верхняя грань
	    buffer.pos(matrix4f, (float) pos1.x, (float) pos2.y, (float) pos1.z).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) pos1.x, (float) pos2.y, (float) pos2.z).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) pos2.x, (float) pos2.y, (float) pos2.z).color(r, g, b, a).endVertex();
	    buffer.pos(matrix4f, (float) pos2.x, (float) pos2.y, (float) pos1.z).color(r, g, b, a).endVertex();

	    Tessellator.getInstance().draw();

	    GL11.glEnable(GL11.GL_DEPTH_TEST);
	    GL11.glDisable(GL11.GL_BLEND);
	}
}
