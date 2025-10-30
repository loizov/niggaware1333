package ru.niggaware.menu.ui.components.module.setting;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class SliderComponent extends Component {
    private String settingName;
    private double min, max, value;
    private boolean dragging = false;
    
    public SliderComponent(int x, int y, int width, int height, String settingName, double min, double max, double value) {
        super(x, y, width, height);
        this.settingName = settingName;
        this.min = min;
        this.max = max;
        this.value = value;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        Render2D.drawRect(matrixStack, x, y, width, height, 0x80000000);
        
        TextRenderer.drawText(matrixStack, settingName, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw slider track
        int sliderX = x + width - 150;
        int sliderY = y + height / 2 - 2;
        int sliderWidth = 100;
        
        Render2D.drawRect(matrixStack, sliderX, sliderY, sliderWidth, 4, 0xFF333333);
        
        // Draw slider handle
        double percentage = (value - min) / (max - min);
        int handleX = sliderX + (int) (sliderWidth * percentage) - 3;
        
        Render2D.drawRect(matrixStack, handleX, sliderY - 3, 6, 10, 0xFF00FF00);
        
        // Draw value
        String valueText = String.format("%.2f", value);
        TextRenderer.drawText(matrixStack, valueText, sliderX + sliderWidth + 5, y + height / 2 - 4, 0xFFFFFFFF);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            dragging = true;
            updateValue(mouseX);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return false;
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            updateValue(mouseX);
            return true;
        }
        return false;
    }
    
    private void updateValue(double mouseX) {
        int sliderX = x + width - 150;
        int sliderWidth = 100;
        
        double percentage = Math.max(0, Math.min(1, (mouseX - sliderX) / sliderWidth));
        value = min + percentage * (max - min);
    }
    
    public double getValue() {
        return value;
    }
    
    public void setValue(double value) {
        this.value = Math.max(min, Math.min(max, value));
    }
}
