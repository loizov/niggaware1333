package ru.niggaware.render.display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;
import ru.niggaware.common.InstanceAccess;

public class Glass implements InstanceAccess {
    
    public static void enableBlend() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    public static void disableBlend() {
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void enableDepth() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
    
    public static void disableDepth() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }
    
    public static void enableStencil() {
        GL11.glEnable(GL11.GL_STENCIL_TEST);
    }
    
    public static void disableStencil() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }
    
    public static void clearStencil() {
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
    }
    
    public static void stencilFunc(int func, int ref, int mask) {
        GL11.glStencilFunc(func, ref, mask);
    }
    
    public static void stencilOp(int fail, int zfail, int zpass) {
        GL11.glStencilOp(fail, zfail, zpass);
    }
}
