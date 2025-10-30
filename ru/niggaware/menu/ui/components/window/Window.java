package ru.niggaware.menu.ui.components.window;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class Window extends Component {
    private String title;
    private boolean dragging = false;
    private int dragOffsetX, dragOffsetY;
    
    public Window(int x, int y, int width, int height, String title) {
        super(x, y, width, height);
        this.title = title;
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        // Draw window background
        Render2D.drawRect(matrixStack, x, y, width, height, 0xCC000000);
        
        // Draw title bar
        Render2D.drawRect(matrixStack, x, y, width, 20, 0xFF333333);
        TextRenderer.drawText(matrixStack, title, x + 5, y + 6, 0xFFFFFFFF);
        
        // Draw close button
        TextRenderer.drawText(matrixStack, "×", x + width - 15, y + 6, 0xFFFFFFFF);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            if (mouseY >= y && mouseY <= y + 20) {
                // Title bar clicked - start dragging
                dragging = true;
                dragOffsetX = (int) (mouseX - x);
                dragOffsetY = (int) (mouseY - y);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return false;
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            setPosition((int) (mouseX - dragOffsetX), (int) (mouseY - dragOffsetY));
            return true;
        }
        return false;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
