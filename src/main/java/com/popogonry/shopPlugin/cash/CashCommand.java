package com.popogonry.shopPlugin.cash;

import com.popogonry.shopPlugin.Reference;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(Reference.prefix_error + "플레이어 명령어 입니다.");
            return true;
        }

        Player player = (Player) commandSender;
        CashService cashService = new CashServiceImpl();
        if(strings.length == 0) {
            player.sendMessage(Reference.prefix_normal + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
            return true;
        }
        else if(strings.length == 1) {
            if(player.isOp()) {
                if(strings[0].equalsIgnoreCase("checkAll")) {
                    player.sendMessage(Reference.prefix_opMessage + "Online Player Cash");
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                         player.sendMessage("   - " + onlinePlayer.getName() + " : " + cashService.getCash(onlinePlayer.getUniqueId()));
                    }
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("store")) {
                    CashRepository cashRepository = new CashRepository();
                    cashRepository.storeCashData(player.getUniqueId());

                    player.sendMessage(CashRepository.cashDataHashMap.toString());

                    return true;
                }
                else if(strings[0].equalsIgnoreCase("load")) {
                    CashRepository cashRepository = new CashRepository();
                    cashRepository.loadCashData(player.getUniqueId());

                    player.sendMessage(CashRepository.cashDataHashMap.toString());
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("show")) {
                    player.sendMessage(CashRepository.cashDataHashMap.toString());
                    return true;
                }


            }
            if(strings[0].equalsIgnoreCase("check")) {
                player.sendMessage(Reference.prefix_normal + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                return true;
            }

        }
        else if(strings.length == 2) {
            if(player.isOp()) {
                if(strings[0].equalsIgnoreCase("set")) {
                    cashService.setCash(player.getUniqueId(), Integer.parseInt(strings[1]));
                    player.sendMessage(Reference.prefix_opMessage + player.getName() + " / = " + strings[1]);
                    player.sendMessage(Reference.prefix_opMessage + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("add")) {
                    cashService.setCash(player.getUniqueId(), cashService.getCash(player.getUniqueId()) + Integer.parseInt(strings[1]));
                    player.sendMessage(Reference.prefix_opMessage + player.getName() + " / + " + strings[1]);
                    player.sendMessage(Reference.prefix_opMessage + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("subtract")) {
                    cashService.setCash(player.getUniqueId(), cashService.getCash(player.getUniqueId()) - Integer.parseInt(strings[1]));
                    player.sendMessage(Reference.prefix_opMessage + player.getName() + " / - " + strings[1]);
                    player.sendMessage(Reference.prefix_opMessage + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("check")) {
                    if(Bukkit.getServer().getPlayer(strings[1]) != null) {
                        Player targerPlayer = Bukkit.getServer().getPlayer(strings[1]);
                        player.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + "의 캐시 : " + cashService.getCash(targerPlayer.getUniqueId()));
                    }
                    else {
                        player.sendMessage(Reference.prefix_error + "플레이어 " + strings[1] + "은 서버에 존재하지 않습니다.");
                    }
                    return true;
                }
            }
            if(strings[0].equalsIgnoreCase("paper")) {

                // check cash
                if(cashService.getCash(player.getUniqueId()) < Integer.parseInt(strings[1])) {
                    player.sendMessage(Reference.prefix_error + "캐시가 부족합니다.");
                    return false;
                }

                // check space from inv
                boolean hasEmptySlot = false;
                for(int slot = 0; slot < 36; slot++) {
                    ItemStack itemStack = player.getInventory().getItem(slot);
                    if(itemStack == null || itemStack.getType() == Material.AIR) hasEmptySlot = true;
                }

                if(hasEmptySlot) {
                    player.getInventory().addItem(cashService.printPaper(player.getUniqueId(), Integer.parseInt(strings[1]), 1));
                    cashService.setCash(player.getUniqueId(), cashService.getCash(player.getUniqueId()) - Integer.parseInt(strings[1]));
                    player.sendMessage(Reference.prefix_normal + strings[1] + "캐시 수표가 " + 1 + "개 발행되었습니다.");
                    player.sendMessage(Reference.prefix_normal + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                }
                else {
                    player.sendMessage(Reference.prefix_error + "인벤토리에 빈 공간이 없습니다.");
                }
                return true;
            }
        }
        else if(strings.length == 3) {
            if(player.isOp()) {
                if(strings[0].equalsIgnoreCase("set")) {
                    Player targerPlayer = Bukkit.getServer().getPlayer(strings[1]);
                    cashService.setCash(targerPlayer.getUniqueId(), Integer.parseInt(strings[2]));
                    player.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + " / = " + strings[2]);
                    player.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + "의 캐시 : " + cashService.getCash(targerPlayer.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("add")) {
                    Player targerPlayer = Bukkit.getServer().getPlayer(strings[1]);
                    cashService.setCash(targerPlayer.getUniqueId(), cashService.getCash(targerPlayer.getUniqueId()) + Integer.parseInt(strings[2]));
                    player.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + " / + " + strings[2]);
                    player.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + "의 캐시 : " + cashService.getCash(targerPlayer.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("subtract")) {
                    Player targerPlayer = Bukkit.getServer().getPlayer(strings[1]);
                    cashService.setCash(targerPlayer.getUniqueId(), cashService.getCash(targerPlayer.getUniqueId()) - Integer.parseInt(strings[2]));
                    player.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + " / - " + strings[2]);
                    player.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + "의 캐시 : " + cashService.getCash(targerPlayer.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("paper")) {
                    // check cash
                    if(cashService.getCash(player.getUniqueId()) < Integer.parseInt(strings[1]) * Integer.parseInt(strings[2])) {
                        player.sendMessage(Reference.prefix_error + "캐시가 부족합니다.");
                        return false;
                    }

                    // check empty slot
                    int emptySlot = 0;
                    int needEmptySlot = Integer.parseInt(strings[2])/64 + 1;

                    if(Integer.parseInt(strings[2]) % 64 == 0) needEmptySlot--;

                    for(int slot = 0; slot < 36; slot++) {
                        ItemStack itemStack = player.getInventory().getItem(slot);
                        if(itemStack == null || itemStack.getType() == Material.AIR) emptySlot++;
                        if(emptySlot >= needEmptySlot) {

                            for(int i = 0; i < Integer.parseInt(strings[2]) / 64; i++) {
                                player.getInventory().addItem(cashService.printPaper(player.getUniqueId(), Integer.parseInt(strings[1]), 64));
                            }

                            player.getInventory().addItem(cashService.printPaper(player.getUniqueId(), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])%64));


                            cashService.setCash(player.getUniqueId(), cashService.getCash(player.getUniqueId()) - Integer.parseInt(strings[1]) * Integer.parseInt(strings[2]));
                            player.sendMessage(Reference.prefix_normal + strings[1] + "캐시 수표가 " + strings[2] + "개 발행되었습니다.");
                            player.sendMessage(Reference.prefix_normal + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                            return true;
                        }
                    }
                    player.sendMessage(Reference.prefix_error + "인벤토리에 빈 공간이 없습니다.");
                    return true;
                }
            }
        }
        player.sendMessage(Reference.prefix_normal + "Cash Command");
        player.sendMessage(Reference.prefix_normal + "/cash check" + ChatColor.GRAY + " : 캐시를 확인합니다.");
        player.sendMessage(Reference.prefix_normal + "/cash paper [cash] [amount]" + ChatColor.GRAY + " : [cash] 캐시 수표를 [amount] 만큼 발행합니다.");
        return false;
    }
}
