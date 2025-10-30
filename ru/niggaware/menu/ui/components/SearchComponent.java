package ru.niggaware.menu.ui.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class SearchComponent extends Component {
    private String searchText = "";
    private boolean focused = false;
    
    public SearchComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        int backgroundColor = focused ? 0x80444444 : 0x80000000;
        Render2D.drawRect(matrixStack, x, y, width, height, backgroundColor);
        
        String displayText = searchText.isEmpty() ? "Search..." : searchText;
        int textColor = searchText.isEmpty() ? 0xFF666666 : 0xFFFFFFFF;
        TextRenderer.drawText(matrixStack, displayText, x + 5, y + height / 2 - 4, textColor);
        
        // Draw search icon
        TextRenderer.drawText(matrixStack, "🔍", x + width - 20, y + height / 2 - 4, 0xFFFFFFFF);
        
        // Draw cursor if focused
        if (focused) {
            int cursorX = x + 5 + TextRenderer.getTextWidth(searchText);
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
    
    public String getSearchText() {
        return searchText;
    }
    
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
