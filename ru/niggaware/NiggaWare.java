package ru.niggaware;

import ru.niggaware.EventManager;
import ru.niggaware.module.ModuleManager;

public class NiggaWare {
    public static final NiggaWare INSTANCE = new NiggaWare();
    
    public static final String NAME = "NiggaWare";
    public static final String VERSION = "0.1";
    
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public ConfigManager configManager;
    
    public void init() {
        System.out.println("[" + NAME + "] Initializing client version " + VERSION);
        
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        
        configManager.load();
        
        System.out.println("[" + NAME + "] Client initialized successfully!");
    }
    
    public void shutdown() {
        configManager.save();
    }
}
