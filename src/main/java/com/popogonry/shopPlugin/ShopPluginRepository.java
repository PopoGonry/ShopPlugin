package com.popogonry.shopPlugin;

import com.popogonry.shopPlugin.cash.CashRepository;
import com.popogonry.shopPlugin.item.ItemInputMode;
import com.popogonry.shopPlugin.item.ItemRepository;
import com.popogonry.shopPlugin.shop.ShopRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

    public void loadAllPluginData() {
        ShopPluginRepository pluginRepository = new ShopPluginRepository();
        pluginRepository.loadPluginDataConfig();

//         player cash data load
        CashRepository cashRepository = new CashRepository();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Player Cash Data Load Start...");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            cashRepository.loadCashData(onlinePlayer.getUniqueId());
        }
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Player Cash Data Load Complete!");

//        item data load
        ItemRepository itemRepository = new ItemRepository();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Item Data Load Start...");

        itemRepository.loadItemIdSetData();

        for (int itemId : ItemRepository.itemIdSet) {
            itemRepository.loadItemData(itemId);
        }

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Item Data Load Complete!");

//        shop data load
        ShopRepository shopRepository = new ShopRepository();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Data Load Start...");

        shopRepository.loadShopNameSetData();

        for (String shopName : ShopRepository.shopNameSet) {
            shopRepository.loadShopData(shopName);
        }

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Data Load Complete!");
    }

    public void saveAllPluginData() {
        CashRepository cashRepository = new CashRepository();
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Player Cash Data Save Start...");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            cashRepository.saveCashData(onlinePlayer.getUniqueId());
        }
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Player Cash Data Save Complete!");

//        item data save
        ItemRepository itemRepository = new ItemRepository();
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Item Data Save Start...");
        for (int itemId : ItemRepository.itemIdSet) {
            itemRepository.saveItemData(itemId);
        }
        itemRepository.saveItemIdSetData();
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Item Data Save Complete!");

//        shop data save
        ShopRepository shopRepository = new ShopRepository();
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Data Save Start...");
        for (String shopName : ShopRepository.shopNameSet) {
            shopRepository.saveShopData(shopName);
        }
        shopRepository.saveShopNameSetData();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Data Save Complete!");

    }

}
