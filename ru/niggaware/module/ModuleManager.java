package ru.niggaware.module;

import ru.niggaware.module.combat.AntiBot;
import ru.niggaware.module.combat.KillAura;
import ru.niggaware.module.movement.Fly;
import ru.niggaware.module.render.ClickGUIModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();
    
    public ModuleManager() {
        // Combat
        modules.add(new KillAura());
        modules.add(new AntiBot());
        
        // Movement
        modules.add(new Fly());
        
        // Render
        modules.add(new ClickGUIModule());
        
        System.out.println("[ModuleManager] Loaded " + modules.size() + " modules");
    }
    
    public List<Module> getModules() {
        return modules;
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
    
    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
    
    public Module getModuleByClass(Class<? extends Module> clazz) {
        for (Module module : modules) {
            if (module.getClass() == clazz) {
                return module;
            }
        }
        return null;
    }
}
