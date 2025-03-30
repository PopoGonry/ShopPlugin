package com.popogonry.shopPlugin;

import com.popogonry.shopPlugin.item.ItemInputMode;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class ShopPluginRepository {

    public static HashMap<UUID, Object> playerInputModeHashMap = new HashMap<>();
    public static HashMap<UUID, String> playerCurrentInventoryTitleHashMap = new HashMap<>();
    public static HashMap<UUID, Inventory> playerCurrentInventoryHashMap = new HashMap<>();



}
