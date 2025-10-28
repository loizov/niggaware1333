package ru.niggaware.module.render;

import ru.niggaware.module.Module;
import net.minecraft.client.Minecraft;
import ru.niggaware.ui.ClickGUI;
import org.lwjgl.glfw.GLFW;

public class ClickGUIModule extends Module {
    
    public ClickGUIModule() {
        super("ClickGUI", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        Minecraft.getInstance().setScreen(new ClickGUI());
        this.setEnabled(false);
    }
}
