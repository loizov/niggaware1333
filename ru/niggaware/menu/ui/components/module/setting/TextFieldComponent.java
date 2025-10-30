package ru.niggaware.menu.ui.components.module.setting;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class TextFieldComponent extends Component {
    private String settingName;
    private String text = "";
    private boolean focused = false;
    
    public TextFieldComponent(int x, int y, int width, int height, String settingName) {
        super(x, y, width, height);
        this.settingName = settingName;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        int backgroundColor = focused ? 0x80444444 : 0x80000000;
        Render2D.drawRect(matrixStack, x, y, width, height, backgroundColor);
        
        TextRenderer.drawText(matrixStack, settingName, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw text field
        int textFieldX = x + width - 150;
        int textFieldY = y + height / 2 - 6;
        
        Render2D.drawRect(matrixStack, textFieldX, textFieldY, 120, 12, 0xFF222222);
        
        String displayText = text.isEmpty() ? "Enter text..." : text;
        int textColor = text.isEmpty() ? 0xFF666666 : 0xFFFFFFFF;
        TextRenderer.drawText(matrixStack, displayText, textFieldX + 2, textFieldY + 2, textColor);
        
        // Draw cursor if focused
        if (focused) {
            int cursorX = textFieldX + 2 + TextRenderer.getTextWidth(text);
            Render2D.drawRect(matrixStack, cursorX, textFieldY + 2, 1, 8, 0xFFFFFFFF);
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
}
