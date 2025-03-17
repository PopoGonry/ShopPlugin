package com.popogonry.shopPlugin.cash;

import com.popogonry.Reference;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CashEvent implements Listener {


    @EventHandler
    public static void usePaperEvent(PlayerInteractEvent event) {
        if (event.getAction().isRightClick() && event.getItem() != null) {
            ItemStack itemStack = event.getItem();
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();

                boolean isPaper = false;

                for (String string : lore) {
                    if (string.contains("수표")) isPaper = true;
                }

                if (isPaper) {
                    CashService cashService = new CashServiceImpl();
                    Player player = event.getPlayer();

                    int amount = Integer.parseInt(itemStack.getItemMeta().getDisplayName().replaceAll("캐시", "").replaceAll("§6", "").replaceAll(" ", ""));


                    if (player.isSneaking()) {
                        amount *= player.getInventory().getItemInMainHand().getAmount();
                        player.getInventory().getItemInMainHand().setAmount(0);
                    } else {
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                    cashService.setCash(player.getUniqueId(), cashService.getCash(player.getUniqueId()) + amount);
                    player.sendMessage(Reference.prefix_normal + amount + "캐시가 추가되었습니다.");
                    player.sendMessage(Reference.prefix_normal + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                }
            }
        }
    }
}
