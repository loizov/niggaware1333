package ru.niggaware.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import ru.niggaware.common.InstanceAccess;
import ru.niggaware.render.display.Glass;
import ru.niggaware.render.display.font.TextRenderer;
import ru.niggaware.render.display.shape.Blur;
import ru.niggaware.render.display.shape.Round;
import ru.niggaware.render.display.shape.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Screen implements InstanceAccess {
    private final List<CategoryPanel> panels = new ArrayList<>();
    private boolean isDragging = false;
    private CategoryPanel draggedPanel = null;
    private int dragOffsetX, dragOffsetY;

    // Цветовая схема
    private static final int BACKGROUND_COLOR = 0xAA1A1A1A;
    private static final int PANEL_COLOR = 0xCC2A2A2A;
    private static final int ACCENT_COLOR = 0xFF6B4FFF;
    private static final int HOVER_COLOR = 0x88FFFFFF;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int DISABLED_COLOR = 0xFF666666;

    public ClickGUI() {
        super(new StringTextComponent("ClickGUI"));
        initializePanels();
    }

    private void initializePanels() {
        String[] categories = {"Combat", "Movement", "Render", "Player", "World", "Misc"};
        int startX = 50;
        int startY = 50;
        int panelSpacing = 120;

        for (int i = 0; i < categories.length; i++) {
            CategoryPanel panel = new CategoryPanel(
                    categories[i],
                    startX + (i * panelSpacing),
                    startY,
                    110,
                    300
            );
            
            // Добавляем примеры модулей
            panel.addModule(new ModuleButton("KillAura", false));
            panel.addModule(new ModuleButton("Velocity", true));
            panel.addModule(new ModuleButton("AutoClicker", false));
            panel.addModule(new ModuleButton("Criticals", false));
            
            panels.add(panel);
        }
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        // Фон с блюром
        Blur.drawBlur(matrix, 0, 0, width, height, BACKGROUND_COLOR, 10.0f);
        
        for (CategoryPanel panel : panels) {
            panel.render(matrix, mouseX, mouseY);
        }
        
        super.render(matrix, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // ЛКМ
            for (CategoryPanel panel : panels) {
                if (panel.isHoveringHeader(mouseX, mouseY)) {
                    isDragging = true;
                    draggedPanel = panel;
                    dragOffsetX = (int) (mouseX - panel.x);
                    dragOffsetY = (int) (mouseY - panel.y);
                    return true;
                }
                
                if (panel.mouseClicked(mouseX, mouseY)) {
                    return true;
                }
            }
        } else if (button == 1) { // ПКМ
            for (CategoryPanel panel : panels) {
                if (panel.isHoveringHeader(mouseX, mouseY)) {
                    panel.expanded = !panel.expanded;
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = false;
            draggedPanel = null;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging && draggedPanel != null) {
            draggedPanel.x = (int) mouseX - dragOffsetX;
            draggedPanel.y = (int) mouseY - dragOffsetY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // Класс панели категории
    private static class CategoryPanel {
        private String name;
        private int x, y, width, height;
        private boolean expanded = true;
        private final List<ModuleButton> modules = new ArrayList<>();
        private static final int HEADER_HEIGHT = 20;
        private static final int MODULE_HEIGHT = 18;
        private static final double CORNER_RADIUS = 6.0;

        public CategoryPanel(String name, int x, int y, int width, int height) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void addModule(ModuleButton module) {
            modules.add(module);
        }

        public void render(MatrixStack matrix, int mouseX, int mouseY) {
            int totalHeight = HEADER_HEIGHT;
            if (expanded) {
                totalHeight += modules.size() * MODULE_HEIGHT + 5;
            }

            // Фон панели с закругленными углами и блюром
            Blur.drawBlur(matrix, x, y, width, totalHeight, PANEL_COLOR, 5.0f);
            Round.drawRoundedRect(matrix, x, y, width, totalHeight, CORNER_RADIUS, PANEL_COLOR);

            // Заголовок категории
            Round.drawRoundedRect(matrix, x, y, width, HEADER_HEIGHT, CORNER_RADIUS, ACCENT_COLOR);
            
            // Текст категории
            int textWidth = TextRenderer.getTextWidth(name);
            TextRenderer.drawCenteredTextWithShadow(
                    matrix, 
                    name, 
                    x + width / 2.0f, 
                    y + (HEADER_HEIGHT - TextRenderer.getTextHeight()) / 2.0f, 
                    TEXT_COLOR
            );

            // Индикатор раскрытия
            String arrow = expanded ? "▼" : "►";
            TextRenderer.drawTextWithShadow(
                    matrix,
                    arrow,
                    x + width - 12,
                    y + (HEADER_HEIGHT - TextRenderer.getTextHeight()) / 2.0f,
                    TEXT_COLOR
            );

            // Рисуем модули, если панель раскрыта
            if (expanded) {
                int moduleY = y + HEADER_HEIGHT + 5;
                for (ModuleButton module : modules) {
                    module.render(matrix, x + 3, moduleY, width - 6, MODULE_HEIGHT, mouseX, mouseY);
                    moduleY += MODULE_HEIGHT;
                }
            }
        }

        public boolean isHoveringHeader(double mouseX, double mouseY) {
            return mouseX >= x && mouseX <= x + width &&
                   mouseY >= y && mouseY <= y + HEADER_HEIGHT;
        }

        public boolean mouseClicked(double mouseX, double mouseY) {
            if (!expanded) return false;
            
            int moduleY = y + HEADER_HEIGHT + 5;
            for (ModuleButton module : modules) {
                if (mouseX >= x + 3 && mouseX <= x + width - 3 &&
                    mouseY >= moduleY && mouseY <= moduleY + MODULE_HEIGHT) {
                    module.toggle();
                    return true;
                }
                moduleY += MODULE_HEIGHT;
            }
            return false;
        }
    }

    // Класс кнопки модуля
    private static class ModuleButton {
        private String name;
        private boolean enabled;
        private static final double CORNER_RADIUS = 4.0;

        public ModuleButton(String name, boolean enabled) {
            this.name = name;
            this.enabled = enabled;
        }

        public void render(MatrixStack matrix, int x, int y, int width, int height, int mouseX, int mouseY) {
            boolean hovering = mouseX >= x && mouseX <= x + width &&
                             mouseY >= y && mouseY <= y + height;

            // Фон кнопки
            int backgroundColor = enabled ? ACCENT_COLOR : 0x55333333;
            if (hovering) {
                backgroundColor = enabled ? 0xFF8B6FFF : HOVER_COLOR;
            }

            Round.drawRoundedRect(matrix, x, y, width, height, CORNER_RADIUS, backgroundColor);

            // Индикатор включения
            if (enabled) {
                ShapeRenderer.drawCircle(matrix, x + 8, y + height / 2.0, 3, 0xFF00FF00);
            } else {
                ShapeRenderer.drawCircle(matrix, x + 8, y + height / 2.0, 3, DISABLED_COLOR);
            }

            // Текст модуля
            int textColor = enabled ? TEXT_COLOR : DISABLED_COLOR;
            TextRenderer.drawTextWithShadow(
                    matrix,
                    name,
                    x + 18,
                    y + (height - TextRenderer.getTextHeight()) / 2.0f,
                    textColor
            );
        }

        public void toggle() {
            enabled = !enabled;
        }
    }
}
