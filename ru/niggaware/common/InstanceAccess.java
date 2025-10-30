package ru.niggaware.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;

public interface InstanceAccess {
    // Безопасный доступ к синглтону Minecraft без статических ссылок на устаревшие объекты
    Minecraft mc = Minecraft.getInstance();

    // Безопасное получение текущего мира
    default ClientWorld getWorldSafe() {
        if (mc == null) return null;
        return mc.world;
    }

    // Пример безопасного доступа к другим сущностям можно вынести в реализации
}
