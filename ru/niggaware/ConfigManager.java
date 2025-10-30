package ru.niggaware;

import com.google.gson.*;
import ru.niggaware.module.Module;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Files;

public class ConfigManager {
    private final File configFile;
    private final Gson gson;
    
    public ConfigManager() {
        File dir = new File(Minecraft.getInstance().gameDir, "NiggaWare");
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        this.configFile = new File(dir, "config.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public void save() {
        try {
            JsonObject json = new JsonObject();
            
            for (Module module : NiggaWare.INSTANCE.moduleManager.getModules()) {
                JsonObject moduleJson = new JsonObject();
                moduleJson.addProperty("enabled", module.isEnabled());
                moduleJson.addProperty("key", module.getKey());
                
                json.add(module.getName(), moduleJson);
            }
            
            FileWriter writer = new FileWriter(configFile);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void load() {
        if (!configFile.exists()) {
            return;
        }
        
        try {
            String content = new String(Files.readAllBytes(configFile.toPath()));
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            
            for (Module module : NiggaWare.INSTANCE.moduleManager.getModules()) {
                if (json.has(module.getName())) {
                    JsonObject moduleJson = json.getAsJsonObject(module.getName());
                    
                    if (moduleJson.has("enabled") && moduleJson.get("enabled").getAsBoolean()) {
                        module.toggle();
                    }
                    
                    if (moduleJson.has("key")) {
                        module.setKey(moduleJson.get("key").getAsInt());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
