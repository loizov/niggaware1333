package ru.niggaware.menu.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class AccountComponent extends Component {
    private String username = "Player";
    private boolean loggedIn = false;
    
    public AccountComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        int backgroundColor = loggedIn ? 0x8000FF00 : 0x80FF0000;
        Render2D.drawRect(matrixStack, x, y, width, height, backgroundColor);
        
        String status = loggedIn ? "Logged in as: " + username : "Not logged in";
        TextRenderer.drawText(matrixStack, status, x + 5, y + height / 2 - 4, 0xFFFFFFFF);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            // Toggle login status for demo
            loggedIn = !loggedIn;
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
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
