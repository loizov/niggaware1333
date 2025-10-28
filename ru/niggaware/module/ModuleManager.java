package ru.niggaware.module;

import ru.niggaware.module.combat.*;
import ru.niggaware.module.movement.*;
import ru.niggaware.module.render.*;


import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    
    private List<Module> modules;
    
    public ModuleManager() {
        modules = new ArrayList<>();
    }
    
    public void init() {
        // COMBAT модули
        modules.add(new KillAura());
        modules.add(new AntiBot());
        
        // MOVEMENT модули
        modules.add(new Fly());
        
        // PLAYER модули
        
        // RENDER модули
        modules.add(new ClickGUIModule());
        
        // MISC модули

        
        System.out.println("Loaded " + modules.size() + " modules");
    }
    
    public void onUpdate() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }
    
    public void onRender() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onRender();
            }
        }
    }
    
    public void onKeyPressed(int keyCode) {
        for (Module module : modules) {
            if (module.getKeyCode() == keyCode) {
                module.toggle();
            }
        }
    }
    
    public List<Module> getModules() {
        return modules;
    }
    
    public Module getModule(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
    
    public List<Module> getModulesByCategory(Module.Category category) {
        List<Module> categoryModules = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                categoryModules.add(module);
            }
        }
        return categoryModules;
    }
}
