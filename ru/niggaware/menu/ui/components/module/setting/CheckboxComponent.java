package ru.niggaware.menu.ui.components.module.setting;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class CheckboxComponent extends Component {
    private String settingName;
    private boolean checked = false;
    
    public CheckboxComponent(int x, int y, int width, int height, String settingName, boolean checked) {
        super(x, y, width, height);
        this.settingName = settingName;
        this.checked = checked;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        Render2D.drawRect(matrixStack, x, y, width, height, 0x80000000);
        
        TextRenderer.drawText(matrixStack, settingName, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw checkbox
        int checkboxX = x + width - 20;
        int checkboxY = y + height / 2 - 6;
        
        Render2D.drawRect(matrixStack, checkboxX, checkboxY, 12, 12, 0xFF333333);
        Render2D.drawRect(matrixStack, checkboxX + 1, checkboxY + 1, 10, 10, 0xFF666666);
        
        if (checked) {
            Render2D.drawRect(matrixStack, checkboxX + 3, checkboxY + 3, 6, 6, 0xFF00FF00);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            checked = !checked;
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
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
