package com.popogonry.shopPlugin.shop;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Shop implements ConfigurationSerializable {

    private String name;
    private HashMap<Integer, Integer> itemHashMap = new HashMap<>();
    private int size;


    public Shop(String name, HashMap<Integer, Integer> itemHashMap) {
        this.name = name;
        this.itemHashMap = itemHashMap;
        this.size = 1;
    }

    public Shop(String name, HashMap<Integer, Integer> itemHashMap, int size) {
        this.name = name;
        this.itemHashMap = itemHashMap;
        this.size = size;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("itemHashMap", itemHashMap);
        map.put("col", size);

        return map;
    }

    public static Shop deserialize(Map<String, Object> map) {
        return new Shop(
                (String) map.get("name"),
                (HashMap<Integer, Integer>) map.get("itemHashMap"),
                (Integer) map.get("col")

        );
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", itemHashMap=" + itemHashMap +
                ", col=" + size +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Integer> getItemHashMap() {
        return itemHashMap;
    }

    public void setItemHashMap(HashMap<Integer, Integer> itemHashMap) {
        this.itemHashMap = itemHashMap;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
