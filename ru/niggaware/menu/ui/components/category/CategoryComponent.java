package ru.niggaware.menu.ui.components.category;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class CategoryComponent extends Component {
    private String name;
    private boolean expanded = false;
    
    public CategoryComponent(int x, int y, int width, int height, String name) {
        super(x, y, width, height);
        this.name = name;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        // Draw category background
        Render2D.drawRect(matrixStack, x, y, width, height, 0x80000000);
        
        // Draw category name
        TextRenderer.drawText(matrixStack, name, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw expand/collapse indicator
        String indicator = expanded ? "-" : "+";
        TextRenderer.drawText(matrixStack, indicator, x + width - 15, y + height / 2 - 4, 0xFFFFFFFF);
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
}
