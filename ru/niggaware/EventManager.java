package ru.niggaware;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {
    
    private HashMap<Class<? extends Event>, List<MethodData>> eventMap;
    
    public EventManager() {
        eventMap = new HashMap<>();
    }
    
    public void register(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() == 1 && method.isAnnotationPresent(EventTarget.class)) {
                Class<?> eventClass = method.getParameterTypes()[0];
                
                if (!eventMap.containsKey(eventClass)) {
                    eventMap.put((Class<? extends Event>) eventClass, new CopyOnWriteArrayList<>());
                }
                
                eventMap.get(eventClass).add(new MethodData(object, method));
            }
        }
    }
    
    public void unregister(Object object) {
        for (List<MethodData> dataList : eventMap.values()) {
            dataList.removeIf(data -> data.getSource() == object);
        }
    }
    
    public Event call(Event event) {
        List<MethodData> dataList = eventMap.get(event.getClass());
        
        if (dataList != null) {
            for (MethodData data : dataList) {
                try {
                    data.getMethod().invoke(data.getSource(), event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return event;
    }
    
    private static class MethodData {
        private Object source;
        private Method method;
        
        public MethodData(Object source, Method method) {
            this.source = source;
            this.method = method;
        }
        
        public Object getSource() {
            return source;
        }
        
        public Method getMethod() {
            return method;
        }
    }
}
