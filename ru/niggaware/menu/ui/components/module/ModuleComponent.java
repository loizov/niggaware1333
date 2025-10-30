package ru.niggaware.menu.ui.components.module;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class ModuleComponent extends Component {
    private String moduleName;
    private boolean enabled = false;
    
    public ModuleComponent(int x, int y, int width, int height, String moduleName) {
        super(x, y, width, height);
        this.moduleName = moduleName;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        int backgroundColor = enabled ? 0x8000FF00 : 0x80000000;
        Render2D.drawRect(matrixStack, x, y, width, height, backgroundColor);
        
        TextRenderer.drawText(matrixStack, moduleName, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw enabled/disabled indicator
        String indicator = enabled ? "ON" : "OFF";
        TextRenderer.drawText(matrixStack, indicator, x + width - 25, y + height / 2 - 4, enabled ? 0xFF00FF00 : 0xFFFF0000);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            enabled = !enabled;
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
    
    public boolean isModuleEnabled() {
        return enabled;
    }
    
    public void setModuleEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
