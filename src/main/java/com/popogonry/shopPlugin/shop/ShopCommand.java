package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.Reference;
import com.popogonry.shopPlugin.item.Item;
import com.popogonry.shopPlugin.item.ItemRepository;
import com.popogonry.shopPlugin.item.ItemService;
import com.popogonry.shopPlugin.item.ItemServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(strings.length == 1) {
            if(commandSender.isOp()) {
//                if(strings[0].equalsIgnoreCase("show")) {
//                    commandSender.sendMessage(ShopRepository.shopDataHashMap.toString());
//                    commandSender.sendMessage(ShopRepository.shopNameSet.toString());
//                    return true;
//                }
                if(strings[0].equalsIgnoreCase("list")) {
                    if(commandSender instanceof Player) {
                        ShopGUI shopGUI = new ShopGUIImpl();
                        shopGUI.openShopListGUI(((Player) commandSender).getPlayer(), 1);
                    }
                    else {
                        commandSender.sendMessage(Reference.prefix_error + "플레이어 전용 명령어 입니다.");
                    }
                    return true;
                }
            }
        }

        else if(strings.length == 2) {
            if(commandSender.isOp()) {
//                if(strings[0].equalsIgnoreCase("create")) {
//                    ShopService shopService = new ShopServiceImpl();
//                    for (int i = 0; i < 30; i++) {
//                        shopService.createShop(new Shop(strings[1] + " Shop " + i, new HashMap<>()));
//                    }
//
//                }
//                else if(strings[0].equalsIgnoreCase("remove")) {
//                    ShopService shopService = new ShopServiceImpl();
//                    boolean commandResult = shopService.removeShop(strings[1]);
//
//                    commandSender.sendMessage("commandResult = " + commandResult);
//
//                }
            }
        }
        else if(strings.length == 3) {
            if(commandSender.isOp()) {
                if(strings[0].equalsIgnoreCase("open")) {
                    ShopGUI shopGUI = new ShopGUIImpl();
                    if(Bukkit.getPlayer(strings[1]) != null) {
                        if(ShopRepository.shopNameSet.contains(strings[2])) {
                            shopGUI.openShopGUI(Bukkit.getPlayer(strings[1]), strings[2], 1);
                        }
                        else {
                            commandSender.sendMessage(Reference.prefix_error + strings[2] + " 상점이 존재하지 않습니다.");
                        }
                    }
                    else {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어가 서버에 존재하지 않습니다.");
                    }
                    return true;
                }
            }
        }
        if(commandSender.isOp()) {
            commandSender.sendMessage(Reference.prefix_normal + "CashShop Command [캐시상점 명령어]");
            commandSender.sendMessage(Reference.prefix_normal + "/cashshop list" + ChatColor.GRAY + " : 캐시 상점 리스트 GUI을 엽니다.");
            commandSender.sendMessage(Reference.prefix_normal + "/cashshop open <Player> <CashShop Name>" + ChatColor.GRAY + " : 캐시 상점 GUI을 엽니다.");
        }
        return false;
    }
}
