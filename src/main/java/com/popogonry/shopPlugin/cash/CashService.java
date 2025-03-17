package com.popogonry.shopPlugin.cash;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface CashService {

    void setCash(UUID uuid, int amount);
    int getCash(UUID uuid);
    ItemStack printPaper(UUID uuid, int amount, int pieces);

}
