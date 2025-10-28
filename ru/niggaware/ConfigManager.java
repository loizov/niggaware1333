package ru.niggaware;

import ru.niggaware.module.Module;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    
    private File configFile;
    private File configDir;
    
    public ConfigManager() {
        configDir = new File("niggaware");
        configFile = new File(configDir, "config.txt");
        
        if (!configDir.exists()) {
            configDir.mkdir();
        }
    }
    
    public void save() {
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            
            PrintWriter writer = new PrintWriter(new FileWriter(configFile));
            
            for (Module module : NiggaWare.getInstance().getModuleManager().getModules()) {
                writer.println(module.getName() + ":" + module.isEnabled() + ":" + module.getKeyCode());
            }
            
            writer.close();
            System.out.println("Config saved!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void load() {
        try {
            if (!configFile.exists()) {
                return;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                if (split.length >= 3) {
                    Module module = NiggaWare.getInstance().getModuleManager().getModule(split[0]);
                    if (module != null) {
                        module.setEnabled(Boolean.parseBoolean(split[1]));
                        module.setKeyCode(Integer.parseInt(split[2]));
                    }
                }
            }
            
            reader.close();
            System.out.println("Config loaded!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
