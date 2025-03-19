package com.popogonry.shopPlugin;

import com.popogonry.shopPlugin.cash.CashCommand;
import com.popogonry.shopPlugin.cash.CashEvent;
import com.popogonry.shopPlugin.cash.CashRepository;
import com.popogonry.shopPlugin.item.Item;
import com.popogonry.shopPlugin.item.ItemCommand;
import com.popogonry.shopPlugin.item.ItemRepository;
import com.popogonry.shopPlugin.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public final class ShopPlugin extends JavaPlugin {

    private static ShopPlugin serverInstance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        serverInstance = this;
        ConfigurationSerialization.registerClass(Item.class);
        ConfigurationSerialization.registerClass(Shop.class);


        getServer().getPluginManager().registerEvents(new ShopPluginEvent(), this);
        getServer().getPluginManager().registerEvents(new CashEvent(), this);
        getServer().getPluginCommand("cash").setExecutor(new CashCommand());
        getServer().getPluginCommand("item").setExecutor(new ItemCommand());


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
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Data Load Start...");




        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Data Load Complete!");






        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Plugin Enabled");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

//        player cash data store
        CashRepository cashRepository = new CashRepository();
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Player Cash Data Store Start...");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            cashRepository.storeCashData(onlinePlayer.getUniqueId());
        }
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Player Cash Data Store Complete!");

//        item data store
        ItemRepository itemRepository = new ItemRepository();
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Item Data Store Start...");
        for (int itemId : ItemRepository.itemIdSet) {
            itemRepository.storeItemData(itemId);
        }
        itemRepository.storeItemIdSetData();
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Item Data Store Complete!");




        serverInstance = null;

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Plugin Disabled");

    }

    public static ShopPlugin getServerInstance() {
        return serverInstance;
    }
}
