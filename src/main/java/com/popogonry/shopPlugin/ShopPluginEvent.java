package com.popogonry.shopPlugin;

import com.popogonry.shopPlugin.cash.CashRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ShopPluginEvent implements Listener {

    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        CashRepository cashRepository = new CashRepository();
        cashRepository.loadCashData(player.getUniqueId());
    }

    @EventHandler
    public static void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        CashRepository cashRepository = new CashRepository();
        cashRepository.storeCashData(player.getUniqueId());
    }



//
//    @EventHandler
//    public static void onClickInventory(InventoryClickEvent event) {
//        Player player = (Player) event.getWhoClicked();
//        if(player.getName().equalsIgnoreCase("PopoGonry")){
//            player.sendMessage(String.valueOf(event.getRawSlot()));
//        }
//    }

}

