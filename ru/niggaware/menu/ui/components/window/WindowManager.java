package ru.niggaware.menu.ui.components.window;

import java.util.ArrayList;
import java.util.List;

public class WindowManager {
    private List<Window> windows = new ArrayList<>();
    
    public void addWindow(Window window) {
        windows.add(window);
    }
    
    public void removeWindow(Window window) {
        windows.remove(window);
    }
    
    public List<Window> getWindows() {
        return windows;
    }
    
    public Window getTopWindow() {
        return windows.isEmpty() ? null : windows.get(windows.size() - 1);
    }
    
    public void bringToFront(Window window) {
        if (windows.contains(window)) {
            windows.remove(window);
            windows.add(window);
        }
    }
    
    public void clear() {
        windows.clear();
    }
}
