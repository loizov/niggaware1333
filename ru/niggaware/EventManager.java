package ru.niggaware;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private final Map<Class<? extends Event>, List<MethodData>> eventMap = new HashMap<>();
    
    public void register(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventTarget.class) && method.getParameterCount() == 1) {
                Class<?> eventClass = method.getParameterTypes()[0];
                
                if (!Event.class.isAssignableFrom(eventClass)) {
                    continue;
                }
                
                method.setAccessible(true);
                
                List<MethodData> methods = eventMap.computeIfAbsent(
                    (Class<? extends Event>) eventClass,
                    k -> new ArrayList<>()
                );
                
                methods.add(new MethodData(object, method));
            }
        }
    }
    
    public void unregister(Object object) {
        eventMap.values().forEach(methods -> methods.removeIf(data -> data.source == object));
    }
    
    public void call(Event event) {
        List<MethodData> methods = eventMap.get(event.getClass());
        
        if (methods == null) {
            return;
        }
        
        for (MethodData data : methods) {
            try {
                data.method.invoke(data.source, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static class MethodData {
        private final Object source;
        private final Method method;
        
        public MethodData(Object source, Method method) {
            this.source = source;
            this.method = method;
        }
    }
}
