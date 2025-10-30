package ru.niggaware.render.display.font;

import net.minecraft.client.gui.FontRenderer;
import ru.niggaware.common.InstanceAccess;
import net.minecraft.client.Minecraft;
public class FontEngine implements InstanceAccess {
	// Получение глобального font renderer
	public static FontRenderer getFontRenderer() {
	    return mc.fontRenderer;
	}

	// Ширина строки
	public static int getStringWidth(String text) {
	    return mc.fontRenderer.getStringWidth(text);
	}

	// Высота строки (шрифт)
	public static int getStringHeight() {
	    return mc.fontRenderer.FONT_HEIGHT;
	}

	// Рисование без MatrixStack (совместимо с 1.16.x)
	public static void drawString(String text, int x, int y, int color) {
	    mc.fontRenderer.drawString(null, text, x, y, color);
	}

	// Рисование с тенью
	public static void drawStringWithShadow(String text, int x, int y, int color) {
	    mc.fontRenderer.drawStringWithShadow(null, text, x, y, color);
	}

	// Рисование по центру
	public static void drawCenteredString(String text, int x, int y, int color) {
	    mc.fontRenderer.drawString(null, text, x - getStringWidth(text) / 2, y, color);
	}

	// Рисование по центру со тенью
	public static void drawCenteredStringWithShadow(String text, int x, int y, int color) {
	    mc.fontRenderer.drawStringWithShadow(null, text, x - getStringWidth(text) / 2, y, color);
	}

	// Вспомогательные перегрузки без MatrixStack (для совместимости)
	// Наличие перегрузок с MatrixStack чаще не требуется в MCP 1.16.5
	public static void drawString(FontMatrixWrapper matrix, String text, float x, float y, int color) {
	    // Учитывая 1.16.5, безопасно приводить к int и отрисовывать без matrix
	    mc.fontRenderer.drawString(null, text, (int) x, (int) y, color);
	}

	public static void drawStringWithShadow(FontMatrixWrapper matrix, String text, float x, float y, int color) {
	    mc.fontRenderer.drawStringWithShadow(null, text, (int) x, (int) y, color);
	}

	public static void drawCenteredString(FontMatrixWrapper matrix, String text, float x, float y, int color) {
	    int w = getStringWidth(text);
	    mc.fontRenderer.drawString(null, text, (int) (x - w / 2.0f), (int) y, color);
	}

	public static void drawCenteredStringWithShadow(FontMatrixWrapper matrix, String text, float x, float y, int color) {
	    int w = getStringWidth(text);
	    mc.fontRenderer.drawStringWithShadow(null, text, (int) (x - w / 2.0f), (int) y, color);
	}

	// Заглушка типа для совместимости, если в вашем проекте нужен реальный тип MatrixStack
	public static class FontMatrixWrapper {
	}

	// Приватная ссылка на Minecraft
	private static final Minecraft mc = Minecraft.getInstance();

}