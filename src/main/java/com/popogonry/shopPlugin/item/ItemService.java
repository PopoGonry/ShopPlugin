package com.popogonry.shopPlugin.item;

import org.bukkit.inventory.ItemStack;

public interface ItemService {

    public boolean createItem(Item item);
    public boolean removeItem(int itemId);
    public int getNewItemId();
}
