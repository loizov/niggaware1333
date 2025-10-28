package ru.niggaware;

import java.util.ArrayList;
import java.util.List;

import ru.niggaware.module.ModuleManager;

public class NiggaWare {
    
    private static NiggaWare instance;
    public static final String NAME = "NiggaWare";
    public static final String VERSION = "1.0";
    
    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private EventManager eventManager;
    
    public NiggaWare() {
        instance = this;
    }
    
    public void startClient() {
        System.out.println(NAME + " v" + VERSION + " initializing...");
        
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        
        moduleManager.init();
        configManager.load();
        
        System.out.println(NAME + " successfully loaded!");
    }
    
    public void stopClient() {
        configManager.save();
        System.out.println(NAME + " stopped!");
    }
    
    public static NiggaWare getInstance() {
        return instance;
    }
    
    public ModuleManager getModuleManager() {
        return moduleManager;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public EventManager getEventManager() {
        return eventManager;
    }
}
