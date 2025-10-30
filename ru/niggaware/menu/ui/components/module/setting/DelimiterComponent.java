package ru.niggaware.menu.ui.components.module.setting;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class DelimiterComponent extends Component {
    private String text;
    
    public DelimiterComponent(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        // Draw delimiter line
        Render2D.drawRect(matrixStack, x, y + height / 2, width, 1, 0xFF666666);
        
        // Draw text in center
        int textWidth = TextRenderer.getTextWidth(text);
        TextRenderer.drawText(matrixStack, text, x + width / 2 - textWidth / 2, y + height / 2 - 4, 0xFF999999);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
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
}
