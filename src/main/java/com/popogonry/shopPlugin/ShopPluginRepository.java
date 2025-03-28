package com.popogonry.shopPlugin;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class ShopPluginRepository {

    public static HashMap<UUID, ChatInputMode> playerChatInputModeHashMap = new HashMap<>();
    public static HashMap<UUID, Inventory> playerCurrentInventoryHashMap = new HashMap<>();



}
