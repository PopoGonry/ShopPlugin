package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.item.Item;
import com.popogonry.shopPlugin.item.ItemRepository;
import com.popogonry.shopPlugin.item.ItemService;
import com.popogonry.shopPlugin.item.ItemServiceImpl;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(strings.length == 1) {
            if(commandSender.isOp()) {
                if(strings[0].equalsIgnoreCase("show")) {
                    commandSender.sendMessage(ShopRepository.shopDataHashMap.toString());
                    commandSender.sendMessage(ShopRepository.shopNameSet.toString());
                    return true;
                }
            }
        }

        else if(strings.length == 2) {
            if(commandSender.isOp()) {
                if(strings[0].equalsIgnoreCase("create")) {
                    ShopService shopService = new ShopServiceImpl();
                    boolean commandResult = shopService.createShop(new Shop(strings[1], new HashMap<>()));

                    commandSender.sendMessage("commandResult = " + commandResult);
                }
                else if(strings[0].equalsIgnoreCase("remove")) {
                    ShopService shopService = new ShopServiceImpl();
                    boolean commandResult = shopService.removeShop(strings[1]);

                    commandSender.sendMessage("commandResult = " + commandResult);

                }
            }
        }


        return false;
    }



}
