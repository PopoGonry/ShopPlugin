package com.popogonry.shopPlugin;

import com.popogonry.shopPlugin.item.ItemInputMode;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class ShopPluginRepository {

    public static HashMap<UUID, Object> playerInputModeHashMap = new HashMap<>();
    public static HashMap<UUID, String> playerCurrentInventoryTitleHashMap = new HashMap<>();
    public static HashMap<UUID, Inventory> playerCurrentInventoryHashMap = new HashMap<>();

    private static final String CONFIG_FILE_NAME = "config.yml";
    private final String configBasePath = ShopPlugin.getServerInstance().getDataFolder().getAbsolutePath();
    private final PluginDataConfig pluginDataConfig;
    public static PluginConfig pluginConfig;

    public ShopPluginRepository() {
        this.pluginDataConfig = new PluginDataConfig(this.configBasePath, "config.yml");
    }

    public void reloadConfig() {
        this.pluginDataConfig.reload();
    }

    public void saveConfig() {
        this.pluginDataConfig.store();
    }

    public void loadPluginDataConfig() {
        pluginConfig = this.pluginDataConfig.loadPluginDataConfig();
    }

}
