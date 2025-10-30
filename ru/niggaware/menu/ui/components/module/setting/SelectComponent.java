package ru.niggaware.menu.ui.components.module.setting;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class SelectComponent extends Component {
    private String settingName;
    private String[] options;
    private int selectedIndex = 0;
    
    public SelectComponent(int x, int y, int width, int height, String settingName, String[] options) {
        super(x, y, width, height);
        this.settingName = settingName;
        this.options = options;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        Render2D.drawRect(matrixStack, x, y, width, height, 0x80000000);
        
        TextRenderer.drawText(matrixStack, settingName, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
        
        String selectedOption = selectedIndex < options.length ? options[selectedIndex] : "None";
        TextRenderer.drawText(matrixStack, selectedOption, x + width - 100, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw arrows
        TextRenderer.drawText(matrixStack, "<", x + width - 120, y + height / 2 - 4, 0xFFFFFFFF);
        TextRenderer.drawText(matrixStack, ">", x + width - 20, y + height / 2 - 4, 0xFFFFFFFF);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            if (mouseX > x + width - 120 && mouseX < x + width - 100) {
                // Left arrow clicked
                selectedIndex = (selectedIndex - 1 + options.length) % options.length;
                return true;
            } else if (mouseX > x + width - 20 && mouseX < x + width) {
                // Right arrow clicked
                selectedIndex = (selectedIndex + 1) % options.length;
                return true;
            }
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
    
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    
    public String getSelectedOption() {
        return selectedIndex < options.length ? options[selectedIndex] : "None";
    }
}
