package ru.niggaware.render.display.shape;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;
import ru.niggaware.common.InstanceAccess;

public class ShapeRenderer implements InstanceAccess {
	public static void drawCircle(MatrixStack matrix, double x, double y, double radius, int color) {
	    Matrix4f matrix4f = matrix.getLast().getMatrix();
	    
	    int a = (color >> 24) & 0xFF;
	    int r = (color >> 16) & 0xFF;
	    int g = (color >> 8) & 0xFF;
	    int b = color & 0xFF;

	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    
	    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	    buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
	    
	    // Центр круга
	    buffer.pos(matrix4f, (float) x, (float) y, 0).color(r, g, b, a).endVertex();
	    
	    int segments = 32;
	    for (int i = 0; i <= segments; i++) {
	        double angle = 2.0 * Math.PI * i / segments;
	        double px = x + radius * Math.cos(angle);
	        double py = y + radius * Math.sin(angle);
	        buffer.pos(matrix4f, (float) px, (float) py, 0).color(r, g, b, a).endVertex();
	    }
	    
	    Tessellator.getInstance().draw();
	    
	    GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawCircle(double x, double y, double radius, int color) {
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
	    buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
	    
	    buffer.pos((float) x, (float) y, 0).color(r, g, b, a).endVertex();
	    
	    int segments = 32;
	    for (int i = 0; i <= segments; i++) {
	        double angle = 2.0 * Math.PI * i / segments;
	        double px = x + radius * Math.cos(angle);
	        double py = y + radius * Math.sin(angle);
	        buffer.pos((float) px, (float) py, 0).color(r, g, b, a).endVertex();
	    }
	    
	    Tessellator.getInstance().draw();
	    
	    GL11.glDisable(GL11.GL_BLEND);
	}

}