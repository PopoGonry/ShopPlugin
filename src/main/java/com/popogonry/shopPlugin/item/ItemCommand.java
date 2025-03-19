package com.popogonry.shopPlugin.item;

import com.popogonry.shopPlugin.Reference;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(Reference.prefix_error + "플레이어 명령어 입니다.");
            return true;
        }

        Player player = (Player) commandSender;
        if(strings.length == 1) {
            if (player.isOp()) {
                if (strings[0].equalsIgnoreCase("show")) {
                    player.sendMessage(ItemRepository.itemDataHashMap.toString());
                    player.sendMessage(ItemRepository.itemIdSet.toString());
                    return true;
                }
                else if (strings[0].equalsIgnoreCase("create")) {
                    ItemService itemService = new ItemServiceImpl();
                    itemService.createItem(new Item(new ItemStack(Material.WOODEN_AXE), "test", null, 10000, 1000, 10, false));
                }


            }
        }
        else if(strings.length == 2) {
            if (player.isOp()) {
                if (strings[0].equalsIgnoreCase("store")) {
                    ItemRepository itemRepository = new ItemRepository();
                    if(ItemRepository.itemDataHashMap.containsKey(Integer.parseInt(strings[1]))) {
                        itemRepository.storeItemData(Integer.parseInt(strings[1]));
                    }
                    else {
                        player.sendMessage(strings[1] + " ID 아이템은 존재하지 않음.");
                    }
                    return true;
                }
                else if (strings[0].equalsIgnoreCase("load")) {
                    ItemRepository itemRepository = new ItemRepository();
                    if(ItemRepository.itemIdSet.contains(Integer.parseInt(strings[1]))) {
                        itemRepository.loadItemData(Integer.parseInt(strings[1]));
                    }
                    else {
                        player.sendMessage(strings[1] + " ID 아이템은 존재하지 않음.");
                    }
                    return true;
                }
                else if (strings[0].equalsIgnoreCase("storeSet")) {
                    ItemRepository itemRepository = new ItemRepository();
                    itemRepository.storeItemIdSetData();
                    return true;
                }
                else if (strings[0].equalsIgnoreCase("loadSet")) {
                    ItemRepository itemRepository = new ItemRepository();
                    itemRepository.loadItemIdSetData();
                    return true;
                }
                else if (strings[0].equalsIgnoreCase("remove")) {
                     if(ItemRepository.itemIdSet.contains(Integer.parseInt(strings[1]))) {
                         ItemService itemService = new ItemServiceImpl();
                         itemService.removeItem(Integer.parseInt(strings[1]));
                     }
                     else {
                         player.sendMessage(strings[1] + " ID 아이템은 존재하지 않음.");
                     }
                }
            }
        }


        return false;
    }
}
