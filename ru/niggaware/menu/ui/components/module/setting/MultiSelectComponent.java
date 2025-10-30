package ru.niggaware.menu.ui.components.module.setting;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class MultiSelectComponent extends Component {
    private String settingName;
    private String[] options;
    private boolean[] selected;
    
    public MultiSelectComponent(int x, int y, int width, int height, String settingName, String[] options) {
        super(x, y, width, height);
        this.settingName = settingName;
        this.options = options;
        this.selected = new boolean[options.length];
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        Render2D.drawRect(matrixStack, x, y, width, height, 0x80000000);
        
        TextRenderer.drawText(matrixStack, settingName, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw selected options count
        int selectedCount = 0;
        for (boolean sel : selected) {
            if (sel) selectedCount++;
        }
        
        String countText = selectedCount + "/" + options.length;
        TextRenderer.drawText(matrixStack, countText, x + width - 50, y + height / 2 - 4, 0xFFFFFFFF);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            // Toggle first option for simplicity
            selected[0] = !selected[0];
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
    
    public boolean[] getSelected() {
        return selected;
    }
    
    public void setSelected(int index, boolean selected) {
        if (index >= 0 && index < this.selected.length) {
            this.selected[index] = selected;
        }
    }
}
