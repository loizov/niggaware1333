package ru.niggaware.menu.ui.components.module.setting;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class ColorPickerComponent extends Component {
    private String settingName;
    private int color = 0xFFFFFFFF;
    private boolean expanded = false;
    
    public ColorPickerComponent(int x, int y, int width, int height, String settingName, int color) {
        super(x, y, width, height);
        this.settingName = settingName;
        this.color = color;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        Render2D.drawRect(matrixStack, x, y, width, height, 0x80000000);
        
        TextRenderer.drawText(matrixStack, settingName, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw color preview
        int colorX = x + width - 30;
        int colorY = y + height / 2 - 6;
        
        Render2D.drawRect(matrixStack, colorX, colorY, 20, 12, color);
        Render2D.drawRect(matrixStack, colorX + 1, colorY + 1, 18, 10, 0xFF000000);
        Render2D.drawRect(matrixStack, colorX + 2, colorY + 2, 16, 8, color);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            expanded = !expanded;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }
    
    public boolean isExpanded() {
        return expanded;
    }
    
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
}
