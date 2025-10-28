package ru.niggaware.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import ru.niggaware.NiggaWare;
import ru.niggaware.module.Module;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Screen {
    
    private List<Frame> frames;
    
    public ClickGUI() {
        super(new StringTextComponent("ClickGUI"));
        frames = new ArrayList<>();
        
        int x = 10;
        for (Module.Category category : Module.Category.values()) {
            frames.add(new Frame(category, x, 10, 110, 15));
            x += 120;
        }
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Полупрозрачный фон
        this.fillGradient(matrixStack, 0, 0, this.width, this.height, 0x50000000, 0x50000000);
        
        for (Frame frame : frames) {
            frame.render(matrixStack, mouseX, mouseY);
        }
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseClicked((int) mouseX, (int) mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseReleased((int) mouseX, (int) mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        for (Frame frame : frames) {
            frame.mouseDragged((int) mouseX, (int) mouseY, button);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    public static class Frame {
        
        private Module.Category category;
        private int x, y, width, height;
        private boolean dragging;
        private int dragX, dragY;
        private boolean extended;
        private List<ModuleButton> buttons;
        
        public Frame(Module.Category category, int x, int y, int width, int height) {
            this.category = category;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.extended = true;
            this.buttons = new ArrayList<>();
            
            int offset = height + 2;
            for (Module module : NiggaWare.getInstance().getModuleManager().getModulesByCategory(category)) {
                buttons.add(new ModuleButton(module, x, y + offset, width, height));
                offset += height + 2;
            }
        }
        
        public void render(MatrixStack matrixStack, int mouseX, int mouseY) {
            // Рисуем заголовок категории
            drawRect(matrixStack, x, y, x + width, y + height, 0xFF1a1a1a);
            drawRect(matrixStack, x, y, x + width, y + height, 0xFF2a2a2a);
            drawString(matrixStack, category.name(), x + 3, y + 4, 0xFFFFFFFF);
            
            if (extended) {
                int offset = height + 2;
                for (ModuleButton button : buttons) {
                    button.x = x;
                    button.y = y + offset;
                    button.render(matrixStack, mouseX, mouseY);
                    offset += height + 2;
                }
            }
        }
        
        public void mouseClicked(int mouseX, int mouseY, int button) {
            // Проверка клика по заголовку
            if (isHovered(mouseX, mouseY, x, y, width, height)) {
                if (button == 0) { // ЛКМ - перетаскивание
                    dragging = true;
                    dragX = mouseX - x;
                    dragY = mouseY - y;
                } else if (button == 1) { // ПКМ - сворачивание
                    extended = !extended;
                }
            }
            
            if (extended) {
                for (ModuleButton moduleButton : buttons) {
                    moduleButton.mouseClicked(mouseX, mouseY, button);
                }
            }
        }
        
        public void mouseReleased(int mouseX, int mouseY, int button) {
            if (button == 0) {
                dragging = false;
            }
        }
        
        public void mouseDragged(int mouseX, int mouseY, int button) {
            if (dragging) {
                x = mouseX - dragX;
                y = mouseY - dragY;
            }
        }
    }
    
    public static class ModuleButton {
        
        private Module module;
        private int x, y, width, height;
        
        public ModuleButton(Module module, int x, int y, int width, int height) {
            this.module = module;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public void render(MatrixStack matrixStack, int mouseX, int mouseY) {
            int color = module.isEnabled() ? 0xFF00aa00 : 0xFF1a1a1a;
            
            if (isHovered(mouseX, mouseY, x, y, width, height)) {
                color = module.isEnabled() ? 0xFF00ff00 : 0xFF2a2a2a;
            }
            
            drawRect(matrixStack, x, y, x + width, y + height, color);
            drawString(matrixStack, module.getName(), x + 3, y + 4, 0xFFFFFFFF);
        }
        
        public void mouseClicked(int mouseX, int mouseY, int button) {
            if (isHovered(mouseX, mouseY, x, y, width, height)) {
                if (button == 0) { // ЛКМ - toggle модуля
                    module.toggle();
                }
            }
        }
    }
    
    // Вспомогательные методы рендеринга
    private static void drawRect(MatrixStack matrixStack, int left, int top, int right, int bottom, int color) {
        Screen.fill(matrixStack, left, top, right, bottom, color);
    }
    
    private static void drawString(MatrixStack matrixStack, String text, int x, int y, int color) {
        net.minecraft.client.Minecraft.getInstance().font.draw(matrixStack, text, x, y, color);
    }
    
    private static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
