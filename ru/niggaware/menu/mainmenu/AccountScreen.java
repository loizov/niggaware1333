package ru.niggaware.menu.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import ru.niggaware.common.InstanceAccess;
import ru.niggaware.menu.mainmenu.AccountComponent;
import ru.niggaware.menu.mainmenu.Button;
import ru.niggaware.menu.mainmenu.TextField;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class AccountScreen extends Screen implements InstanceAccess {
    private AccountComponent accountComponent;
    private TextField usernameField;
    private TextField passwordField;
    private Button loginButton;
    private Button backButton;
    
    public AccountScreen() {
        super(new StringTextComponent("Account"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = width / 2;
        int centerY = height / 2;
        
        accountComponent = new AccountComponent(centerX - 100, centerY - 100, 200, 30);
        usernameField = new TextField(centerX - 100, centerY - 50, 200, 20, "Username");
        passwordField = new TextField(centerX - 100, centerY - 20, 200, 20, "Password");
        loginButton = new Button(centerX - 50, centerY + 10, 100, 20, "Login");
        backButton = new Button(centerX - 50, centerY + 40, 100, 20, "Back");
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        
        TextRenderer.drawCenteredText(matrixStack, "Account Management", width / 2, 50, 0xFFFFFFFF);
        
        accountComponent.render(matrixStack, mouseX, mouseY, partialTicks);
        usernameField.render(matrixStack, mouseX, mouseY, partialTicks);
        passwordField.render(matrixStack, mouseX, mouseY, partialTicks);
        loginButton.render(matrixStack, mouseX, mouseY, partialTicks);
        backButton.render(matrixStack, mouseX, mouseY, partialTicks);
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (loginButton.mouseClicked(mouseX, mouseY, button)) {
            // Handle login
            accountComponent.setUsername(usernameField.getText());
            accountComponent.setLoggedIn(true);
            return true;
        }
        
        if (backButton.mouseClicked(mouseX, mouseY, button)) {
            mc.displayGuiScreen(null);
            return true;
        }
        
        usernameField.mouseClicked(mouseX, mouseY, button);
        passwordField.mouseClicked(mouseX, mouseY, button);
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
