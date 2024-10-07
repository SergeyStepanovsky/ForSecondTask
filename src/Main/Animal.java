package Main;

import java.util.*;

// Класс Animal представляет каждое животное с его свойствами
public class Animal {
    private Map<String, String> properties = new HashMap<>();

    public void addProperty(String property, String parameter) {
        properties.put(property, parameter);
    }

    public String getProperty(String property) {
        return properties.get(property);
    }
}


