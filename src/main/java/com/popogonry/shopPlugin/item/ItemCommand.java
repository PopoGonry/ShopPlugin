package com.popogonry.shopPlugin.item;

import com.popogonry.shopPlugin.Reference;
import com.popogonry.shopPlugin.shop.ShopGUI;
import com.popogonry.shopPlugin.shop.ShopGUIImpl;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1) {
            if (commandSender.isOp()) {
//                if (strings[0].equalsIgnoreCase("show")) {
//                    player.sendMessage(ItemRepository.itemDataHashMap.toString());
//                    player.sendMessage(ItemRepository.itemIdSet.toString());
//                    return true;
//                }
//                else if (strings[0].equalsIgnoreCase("create")) {
//                    ItemService itemService = new ItemServiceImpl();
//                    for (int i = 0; i < 10; i++) {
//                        itemService.createItem(new Item(new ItemStack(Material.WOODEN_AXE), "test", Collections.singletonList("test"), 10000, 1000, 10, 20, true, new Date().getTime(), true, false));
//                    }
//                }
                if (strings[0].equalsIgnoreCase("list")) {
                    if(commandSender instanceof Player) {
                        Player player = (Player) commandSender;
                        ItemGUI itemGUI = new ItemGUIImpl();
                        itemGUI.openItemListGUI(player, 1);
                    }
                    else {
                        commandSender.sendMessage(Reference.prefix_error + "플레이어 전용 명령어 입니다.");
                    }
                    return true;
                }
            }
        }
        else if(strings.length == 2) {
            if (commandSender.isOp()) {
//                if (strings[0].equalsIgnoreCase("store")) {
//                    ItemRepository itemRepository = new ItemRepository();
//                    if(ItemRepository.itemDataHashMap.containsKey(Integer.parseInt(strings[1]))) {
//                        itemRepository.storeItemData(Integer.parseInt(strings[1]));
//                    }
//                    else {
//                        player.sendMessage(strings[1] + " ID 아이템은 존재하지 않음.");
//                    }
//                    return true;
//                }
//                else if (strings[0].equalsIgnoreCase("load")) {
//                    ItemRepository itemRepository = new ItemRepository();
//                    if(ItemRepository.itemIdSet.contains(Integer.parseInt(strings[1]))) {
//                        itemRepository.loadItemData(Integer.parseInt(strings[1]));
//                    }
//                    else {
//                        player.sendMessage(strings[1] + " ID 아이템은 존재하지 않음.");
//                    }
//                    return true;
//                }
//                else if (strings[0].equalsIgnoreCase("storeSet")) {
//                    ItemRepository itemRepository = new ItemRepository();
//                    itemRepository.storeItemIdSetData();
//                    return true;
//                }
//                else if (strings[0].equalsIgnoreCase("loadSet")) {
//                    ItemRepository itemRepository = new ItemRepository();
//                    itemRepository.loadItemIdSetData();
//                    return true;
//                }
//                else if (strings[0].equalsIgnoreCase("remove")) {
//                     if(ItemRepository.itemIdSet.contains(Integer.parseInt(strings[1]))) {
//                         ItemService itemService = new ItemServiceImpl();
//                         itemService.removeItem(Integer.parseInt(strings[1]));
//                     }
//                     else {
//                         player.sendMessage(strings[1] + " ID 아이템은 존재하지 않음.");
//                     }
//                }
            }
        }
        if(commandSender.isOp()) {
            commandSender.sendMessage(Reference.prefix_normal + "CashItem Command [캐시아이템 명령어]");
            commandSender.sendMessage(Reference.prefix_normal + "/cashitem list" + ChatColor.GRAY + " : 캐시 아이템 리스트 GUI을 엽니다.");
        }
        return false;
    }
}
