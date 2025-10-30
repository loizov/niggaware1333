package ru.niggaware.utils;

import ru.niggaware.NiggaWare;
import ru.niggaware.module.Module;

public class KeyboardHandler {
    
    public static void onKeyPress(int key, int action) {
        if (action == 1) { // GLFW_PRESS = 1
            for (Module module : NiggaWare.INSTANCE.moduleManager.getModules()) {
                if (module.getKey() == key) {
                    module.toggle();
                    System.out.println("[KeyBind] Toggled " + module.getName() + " -> " + module.isEnabled());
                }
            }
        }
    }
}
