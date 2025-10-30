package ru.niggaware.menu.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class Button extends Component {
    private String text;
    private Runnable onClick;
    
    public Button(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        boolean hovered = isMouseOver(mouseX, mouseY);
        int backgroundColor = hovered ? 0x80444444 : 0x80222222;
        
        Render2D.drawRect(matrixStack, x, y, width, height, backgroundColor);
        
        // Draw border
        Render2D.drawRect(matrixStack, x, y, width, 1, 0xFF666666);
        Render2D.drawRect(matrixStack, x, y + height - 1, width, 1, 0xFF666666);
        Render2D.drawRect(matrixStack, x, y, 1, height, 0xFF666666);
        Render2D.drawRect(matrixStack, x + width - 1, y, 1, height, 0xFF666666);
        
        TextRenderer.drawCenteredText(matrixStack, text, x + width / 2, y + height / 2 - 4, 0xFFFFFFFF);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            if (onClick != null) {
                onClick.run();
            }
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
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }
}
