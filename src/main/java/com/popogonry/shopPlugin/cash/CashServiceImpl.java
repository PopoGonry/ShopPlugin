package com.popogonry.shopPlugin.cash;

import com.popogonry.shopPlugin.Reference;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CashServiceImpl implements CashService {

    CashRepository cashRepository = new CashRepository();

    @Override
    public void setCash(UUID uuid, int amount) {
        CashRepository.cashDataHashMap.put(uuid, amount);
        cashRepository.saveCashData(uuid);
    }

    @Override
    public int getCash(UUID uuid) {
        return CashRepository.cashDataHashMap.getOrDefault(uuid, 0);
    }

    @Override
    public ItemStack printPaper(UUID uuid, int amount, int pieces) {

        ItemStack itemStack = new ItemStack(Material.PAPER);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "" + amount + "캐시");

        List<String> lore = new ArrayList<String>();
        lore.add(Reference.prefix_normal + ChatColor.GOLD + "수표");
        lore.add(ChatColor.GRAY + "우클릭시 " + amount + "캐시가 추가됩니다.");
        itemMeta.setLore(lore);

        itemStack.setAmount(pieces);

        itemStack.setItemMeta(itemMeta);


        return new ItemStack(itemStack);
    }

}
