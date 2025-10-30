package ru.niggaware.menu.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class TextField extends Component {
    private String placeholder;
    private String text = "";
    private boolean focused = false;
    
    public TextField(int x, int y, int width, int height, String placeholder) {
        super(x, y, width, height);
        this.placeholder = placeholder;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        int backgroundColor = focused ? 0x80444444 : 0x80222222;
        Render2D.drawRect(matrixStack, x, y, width, height, backgroundColor);
        
        // Draw border
        int borderColor = focused ? 0xFF00FF00 : 0xFF666666;
        Render2D.drawRect(matrixStack, x, y, width, 1, borderColor);
        Render2D.drawRect(matrixStack, x, y + height - 1, width, 1, borderColor);
        Render2D.drawRect(matrixStack, x, y, 1, height, borderColor);
        Render2D.drawRect(matrixStack, x + width - 1, y, 1, height, borderColor);
        
        String displayText = text.isEmpty() ? placeholder : text;
        int textColor = text.isEmpty() ? 0xFF666666 : 0xFFFFFFFF;
        TextRenderer.drawText(matrixStack, displayText, x + 5, y + height / 2 - 4, textColor);
        
        // Draw cursor if focused
        if (focused) {
            int cursorX = x + 5 + TextRenderer.getTextWidth(text);
            Render2D.drawRect(matrixStack, cursorX, y + height / 2 - 4, 1, 8, 0xFFFFFFFF);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            focused = true;
            return true;
        } else {
            focused = false;
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
    
    public boolean isFocused() {
        return focused;
    }
    
    public void setFocused(boolean focused) {
        this.focused = focused;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getPlaceholder() {
        return placeholder;
    }
    
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
