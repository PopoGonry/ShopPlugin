package com.popogonry.shopPlugin.shop;

import org.bukkit.entity.Player;

public interface ShopGUI {
    boolean openShopListGUI(Player player, int page);
    boolean openShopSettingGUI(Player player, String shopName);
    boolean openShopItemSettingGUI(Player player, String shopName, int page);
    boolean openItemListtoAddGUI(Player player, int page, String shopName, int slot);
    boolean openShopGUI(Player player, String shopName, int page);
}
