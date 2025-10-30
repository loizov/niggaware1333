package ru.niggaware.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import ru.niggaware.common.InstanceAccess;

public class Generator implements InstanceAccess {
    
    public static Screen createMainMenu() {
        return new ru.niggaware.menu.ui.MenuScreen();
    }
    
    public static Screen createAccountScreen() {
        return new ru.niggaware.menu.mainmenu.AccountScreen();
    }
    
    public static void openMainMenu() {
        mc.displayGuiScreen(createMainMenu());
    }
    
    public static void openAccountScreen() {
        mc.displayGuiScreen(createAccountScreen());
    }
}
