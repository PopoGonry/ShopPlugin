package com.popogonry.shopPlugin;

import com.popogonry.shopPlugin.cash.CashCommand;
import com.popogonry.shopPlugin.cash.CashEvent;
import com.popogonry.shopPlugin.cash.CashPlaceholder;
import com.popogonry.shopPlugin.cash.CashRepository;
import com.popogonry.shopPlugin.item.Item;
import com.popogonry.shopPlugin.item.ItemCommand;
import com.popogonry.shopPlugin.item.ItemGUIEvent;
import com.popogonry.shopPlugin.item.ItemRepository;
import com.popogonry.shopPlugin.shop.Shop;
import com.popogonry.shopPlugin.shop.ShopCommand;
import com.popogonry.shopPlugin.shop.ShopGUIEvent;
import com.popogonry.shopPlugin.shop.ShopRepository;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public final class ShopPlugin extends JavaPlugin {

    private static ShopPlugin serverInstance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        serverInstance = this;
        saveDefaultConfig();

        ConfigurationSerialization.registerClass(Item.class);
        ConfigurationSerialization.registerClass(Shop.class);


        getServer().getPluginManager().registerEvents(new ShopPluginEvent(), this);
        getServer().getPluginManager().registerEvents(new CashEvent(), this);
        getServer().getPluginManager().registerEvents(new ItemGUIEvent(), this);
        getServer().getPluginManager().registerEvents(new ShopGUIEvent(), this);

        getServer().getPluginCommand("cash").setExecutor(new CashCommand());
        getServer().getPluginCommand("cashitem").setExecutor(new ItemCommand());
        getServer().getPluginCommand("cashshop").setExecutor(new ShopCommand());

        ShopPluginRepository pluginRepository = new ShopPluginRepository();
        pluginRepository.loadAllPluginData();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new CashPlaceholder(this).register();
            getLogger().info("Cash Placeholder Registered!");
        } else {
            getLogger().warning("PlaceholderAPI가 설치되지 않았습니다. 커스텀 플레이스홀더를 사용할 수 없습니다!");
        }

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Plugin Enabled");



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        ShopPluginRepository pluginRepository = new ShopPluginRepository();
        pluginRepository.saveAllPluginData();


        serverInstance = null;

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "Shop Plugin Disabled");

    }

    public static ShopPlugin getServerInstance() {
        return serverInstance;
    }
}
