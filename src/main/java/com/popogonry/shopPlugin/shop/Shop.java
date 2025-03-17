package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.item.Item;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop implements ConfigurationSerializable {

    private String name;
    private HashMap<Integer, Integer> itemHashMap = new HashMap<>();
    private int col = 6;

    public Shop(String name, HashMap<Integer, Integer> itemHashMap, int col) {
        this.name = name;
        this.itemHashMap = itemHashMap;
        this.col = col;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("itemHashMap", itemHashMap);
        map.put("col", col);

        return map;
    }

    public static Shop deserialize(Map<String, Object> map) {
        return new Shop(
                (String) map.get("name"),
                (HashMap<Integer, Integer>) map.get("itemHashMap"),
                (Integer) map.get("col")

        );
    }

}
