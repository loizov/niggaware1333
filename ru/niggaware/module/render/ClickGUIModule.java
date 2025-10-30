package ru.niggaware.module.render;

import ru.niggaware.module.Module;
import ru.niggaware.ui.ClickGUI;
import org.lwjgl.glfw.GLFW;

public class ClickGUIModule extends Module {
    
    public ClickGUIModule() {
        super("ClickGUI", Category.RENDER);
        setKey(GLFW.GLFW_KEY_RIGHT_SHIFT);
    }
    
    @Override
    public void onEnable() {
        if (mc.currentScreen == null) {
            mc.displayGuiScreen(new ClickGUI());
        }
        // Сразу выключаем модуль, так как это только открывает GUI
        setEnabled(false);
    }
}
