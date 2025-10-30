package ru.niggaware.menu.ui.components.window;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class ColorPickerWindow extends Component {
    private int selectedColor = 0xFFFFFFFF;
    private boolean visible = false;
    
    public ColorPickerWindow(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        // Draw window background
        Render2D.drawRect(matrixStack, x, y, width, height, 0xCC000000);
        
        // Draw color palette
        int colorSize = 20;
        int colorsPerRow = width / colorSize;
        
        int[] colors = {
            0xFFFFFFFF, 0xFF000000, 0xFFFF0000, 0xFF00FF00, 0xFF0000FF,
            0xFFFFFF00, 0xFFFF00FF, 0xFF00FFFF, 0xFF808080, 0xFF800000,
            0xFF008000, 0xFF000080, 0xFF808000, 0xFF800080, 0xFF008080
        };
        
        for (int i = 0; i < colors.length; i++) {
            int colorX = x + 10 + (i % colorsPerRow) * colorSize;
            int colorY = y + 30 + (i / colorsPerRow) * colorSize;
            
            Render2D.drawRect(matrixStack, colorX, colorY, colorSize - 2, colorSize - 2, colors[i]);
            
            // Highlight selected color
            if (colors[i] == selectedColor) {
                Render2D.drawRect(matrixStack, colorX - 1, colorY - 1, colorSize, colorSize, 0xFFFFFFFF);
            }
        }
        
        // Draw title
        TextRenderer.drawText(matrixStack, "Color Picker", x + 5, y + 5, 0xFFFFFFFF);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!visible || !isMouseOver(mouseX, mouseY)) return false;
        
        if (button == 0) {
            // Check if a color was clicked
            int colorSize = 20;
            int colorsPerRow = width / colorSize;
            
            int[] colors = {
                0xFFFFFFFF, 0xFF000000, 0xFFFF0000, 0xFF00FF00, 0xFF0000FF,
                0xFFFFFF00, 0xFFFF00FF, 0xFF00FFFF, 0xFF808080, 0xFF800000,
                0xFF008000, 0xFF000080, 0xFF808000, 0xFF800080, 0xFF008080
            };
            
            for (int i = 0; i < colors.length; i++) {
                int colorX = x + 10 + (i % colorsPerRow) * colorSize;
                int colorY = y + 30 + (i / colorsPerRow) * colorSize;
                
                if (mouseX >= colorX && mouseX <= colorX + colorSize - 2 &&
                    mouseY >= colorY && mouseY <= colorY + colorSize - 2) {
                    selectedColor = colors[i];
                    return true;
                }
            }
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
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public int getSelectedColor() {
        return selectedColor;
    }
    
    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }
}
