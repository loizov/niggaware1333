package ru.niggaware.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import ru.niggaware.common.InstanceAccess;

public class ScaleUtil implements InstanceAccess {
    public static float size = 2;

    public static void scale_pre() {
        final double scale = mc.getMainWindow().getGuiScaleFactor() / Math.pow(mc.getMainWindow().getGuiScaleFactor(), 2);
        GL11.glPushMatrix();
        GL11.glScaled(scale * size, scale * size, scale * size);
    }


    public static void scale_post() {
        GL11.glScaled(size, size, size);
        GL11.glPopMatrix();
    }


    public static void scaleStart(float x, float y, float scale) {
        RenderSystem.pushMatrix();
        RenderSystem.translated(x, y, 0);
        RenderSystem.scaled(scale, scale, 1);
        RenderSystem.translated(-x, -y, 0);
    }

    public static void scaleEnd() {
        RenderSystem.popMatrix();
    }

    public static void scaleNonMatrix(float x, float y, float scale) {
        RenderSystem.translated(x, y, 0);
        RenderSystem.scaled(scale, scale, 1);
        RenderSystem.translated(-x, -y, 0);
    }


    public static int calc(int value) {
        return (int) (value * mc.getMainWindow().getGuiScaleFactor() / size);
    }

    public static int calc(double value) {
        return (int) (value * mc.getMainWindow().getGuiScaleFactor() / size);
    }

    public static double[] calc(double mouseX, double mouseY) {
        mouseX = mouseX * mc.getMainWindow().getGuiScaleFactor() / size;
        mouseY = mouseY * mc.getMainWindow().getGuiScaleFactor() / size;
        return new double[]{mouseX, mouseY};
    }

    public static int[] calc(int mouseX, int mouseY) {
        mouseX = (int) (mouseX * mc.getMainWindow().getGuiScaleFactor() / size);
        mouseY = (int) (mouseY * mc.getMainWindow().getGuiScaleFactor() / size);
        return new int[]{mouseX, mouseY};
    }
}


