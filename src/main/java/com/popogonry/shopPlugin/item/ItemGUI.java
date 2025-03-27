package com.popogonry.shopPlugin.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ItemGUI {
    boolean openItemListGUI(Player player, int page);
    boolean openItemSettingGUI(Player player, Item item);
    ItemStack getItemStack(Integer itemID);
    ItemStack getItemStackShopVer(Integer itemID);
}
