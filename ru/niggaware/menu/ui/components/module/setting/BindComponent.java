package ru.niggaware.menu.ui.components.module.setting;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;

import ru.niggaware.menu.ui.components.Component;
import ru.niggaware.render.display.Render2D;
import ru.niggaware.render.display.font.TextRenderer;

public class BindComponent extends Component {
    private String settingName;
    private String keyCode;
    private boolean listening = false;

    public BindComponent(int x, int y, int width, int height, String settingName, String keyCode) {
        super(x, y, width, height);
        this.settingName = settingName;
        this.keyCode = keyCode;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;

        Render2D.drawRect(matrixStack, x, y, width, height, 0x80000000);

        TextRenderer.drawText(matrixStack, settingName, x + 5, y + height / 2 - 4, 0xFFFFFFFF);

        String keyText = listening ? "..." : keyCodeToDisplayName(keyCode);
        TextRenderer.drawText(matrixStack, keyText, x + width - 60, y + height / 2 - 4, listening ? 0xFFFFFF00 : 0xFFFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            listening = !listening;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    // Безопасный конвертер к читаемой строке
    private String keyCodeToDisplayName(String keyCode2) {
        // Попытка получить имя через API, доступное в вашей сборке
        try {
            String name = InputMappings.getInputByName(keyCode2).toString();
            if (name != null && !name.isEmpty()) {
                return name;
            }
        } catch (Throwable ignored) {
        }

        return "Key " + keyCode2;
    }
}
