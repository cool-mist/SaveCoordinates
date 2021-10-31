package me.bionicbeanie.mods.savecoords.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.Identifier;

public class IdentifiersCache {
    
    private String DEFAULT = "nonexistent";
    private Map<String, Identifier> cache = new HashMap<>();
    
    public IdentifiersCache() {
        add(DEFAULT, cache);
        add("overworld", cache);
        add("minecraft:overworld", "overworld", cache);
        add("minecraft:the_end", "end", cache);
        add("minecraft:the_nether", "nether", cache);
        add("nether", cache);
        add("end", cache);
        add("ping", cache);
        add("close", cache);
        add("more", cache);
    }
    
    public Identifier get(String resourceName) {
        String key = resourceName.toLowerCase();
        return cache.getOrDefault(key, cache.get(DEFAULT));
    }
    
    private static void add(String identifierName, String resourceName, Map<String, Identifier> cache) {
        cache.put(identifierName, createIdentifier(resourceName));
    }
    
    private static void add(String resourceName, Map<String, Identifier> cache) {
        cache.put(resourceName, createIdentifier(resourceName));
    }
    
    private static Identifier createIdentifier(String file) {
        return new Identifier("savecoords", "textures/gui/" + file + ".png");
    }
}
