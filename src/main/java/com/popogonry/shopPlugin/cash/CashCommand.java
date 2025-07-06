package com.popogonry.shopPlugin.cash;

import com.popogonry.shopPlugin.Reference;
import me.clip.placeholderapi.PlaceholderAPI;
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

//        if(!(commandSender instanceof Player)) {
//            commandSender.sendMessage(Reference.prefix_error + "플레이어 명령어 입니다.");
//            return true;
//        }

        CashService cashService = new CashServiceImpl();
        if(strings.length == 0 && commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.sendMessage(Reference.prefix_normal + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
            return true;
        }
        else if(strings.length == 1) {
            if(commandSender.isOp()) {
                if(strings[0].equalsIgnoreCase("checkAll")) {
                    commandSender.sendMessage(Reference.prefix_opMessage + "Online Player Cash");
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        commandSender.sendMessage("   - " + onlinePlayer.getName() + " : " + cashService.getCash(onlinePlayer.getUniqueId()));
                    }
                    return true;
                }
            }
            if((strings[0].equalsIgnoreCase("check") || strings[0].equalsIgnoreCase("확인")) && commandSender instanceof Player) {
                Player player = (Player) commandSender;
                player.sendMessage(Reference.prefix_normal + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                return true;
            }

        }
        else if(strings.length == 2) {
            if(commandSender.isOp()) {
                if(commandSender instanceof Player) {
                    Player player = (Player) commandSender;
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
                    else if(strings[0].equalsIgnoreCase("subtract") || strings[0].equalsIgnoreCase("sub")) {
                        cashService.setCash(player.getUniqueId(), cashService.getCash(player.getUniqueId()) - Integer.parseInt(strings[1]));
                        player.sendMessage(Reference.prefix_opMessage + player.getName() + " / - " + strings[1]);
                        player.sendMessage(Reference.prefix_opMessage + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));
                        return true;
                    }
                    else if(strings[0].equalsIgnoreCase("paper")) {

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
                if(strings[0].equalsIgnoreCase("check")) {
                    if(Bukkit.getServer().getPlayer(strings[1]) != null) {
                        Player targerPlayer = Bukkit.getServer().getPlayer(strings[1]);
                        commandSender.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + "의 캐시 : " + cashService.getCash(targerPlayer.getUniqueId()));
                    }
                    else {
                        commandSender.sendMessage(Reference.prefix_error + "플레이어 " + strings[1] + "은 서버에 존재하지 않습니다.");
                    }
                    return true;
                }

            }

        }
        else if(strings.length == 3) {
            if(commandSender.isOp()) {
                if(strings[0].equalsIgnoreCase("set")) {
                    Player targerPlayer = Bukkit.getServer().getPlayer(strings[1]);
                    cashService.setCash(targerPlayer.getUniqueId(), Integer.parseInt(strings[2]));
                    commandSender.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + " / = " + strings[2]);
                    commandSender.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + "의 캐시 : " + cashService.getCash(targerPlayer.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("add")) {
                    Player targerPlayer = Bukkit.getServer().getPlayer(strings[1]);
                    cashService.setCash(targerPlayer.getUniqueId(), cashService.getCash(targerPlayer.getUniqueId()) + Integer.parseInt(strings[2]));
                    commandSender.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + " / + " + strings[2]);
                    commandSender.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + "의 캐시 : " + cashService.getCash(targerPlayer.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("subtract")) {
                    Player targerPlayer = Bukkit.getServer().getPlayer(strings[1]);
                    cashService.setCash(targerPlayer.getUniqueId(), cashService.getCash(targerPlayer.getUniqueId()) - Integer.parseInt(strings[2]));
                    commandSender.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + " / - " + strings[2]);
                    commandSender.sendMessage(Reference.prefix_opMessage + targerPlayer.getName() + "의 캐시 : " + cashService.getCash(targerPlayer.getUniqueId()));
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("paper") && commandSender instanceof Player) {
                    Player player = (Player) commandSender;
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

        sendCashCommandHelp(commandSender);
        return false;
    }

    public static void sendCashCommandHelp(CommandSender sender) {
        sender.sendMessage("§7========== §a[캐시 명령어 도움말] §7==========");
        sender.sendMessage("§6▶ 일반 명령어");
        sender.sendMessage(" §f/cash §7또는 §f/캐시 §7: 자신의 캐시를 확인합니다.");
        sender.sendMessage(" §f/cash check §7또는 §f/캐시 확인 §7: 자신의 캐시를 확인합니다.");

        if (sender.isOp()) {
            sender.sendMessage("§6▶ 관리자 명령어");
            sender.sendMessage(" §f/cash checkAll §7: 접속 중인 모든 플레이어의 캐시를 확인합니다.");
            sender.sendMessage(" §f/cash check <플레이어명> §7: 대상 플레이어의 캐시를 확인합니다.");
            sender.sendMessage(" §f/cash set <플레이어명> <금액> §7: 캐시를 설정합니다.");
            sender.sendMessage(" §f/cash add <플레이어명> <금액> §7: 캐시를 추가합니다.");
            sender.sendMessage(" §f/cash subtract <플레이어명> <금액> §7: 캐시를 차감합니다.");
            sender.sendMessage(" §f/cash paper <금액> §7: 자신의 인벤토리에 캐시 수표를 발행합니다.");
            sender.sendMessage(" §f/cash paper <금액> <수량> §7: 여러 장의 캐시 수표를 발행합니다.");
        }

        sender.sendMessage("§7=====================================");
    }
}
