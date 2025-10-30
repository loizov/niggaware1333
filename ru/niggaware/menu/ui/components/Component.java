package ru.niggaware.menu.ui.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import ru.niggaware.common.InstanceAccess;

public abstract class Component implements InstanceAccess {
    protected int x, y, width, height;
    protected boolean visible = true;
    protected boolean enabled = true;
    
    public Component(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public abstract void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);
    
    public abstract boolean mouseClicked(double mouseX, double mouseY, int button);
    
    public abstract boolean mouseReleased(double mouseX, double mouseY, int button);
    
    public abstract boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY);
    
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
}
