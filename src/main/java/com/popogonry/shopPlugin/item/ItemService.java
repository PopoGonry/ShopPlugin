package com.popogonry.shopPlugin.item;

import org.bukkit.inventory.ItemStack;

public interface ItemService {

    boolean createItem(Item item);
    boolean removeItem(int itemId);
    int getNewItemId();

}
