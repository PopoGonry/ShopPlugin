package com.popogonry.shopPlugin.item;

import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class ItemServiceImpl implements ItemService {

    @Override
    public boolean createItem(Item item) {
        int newItemId = getNewItemId();

        if(ItemRepository.itemIdSet.contains(newItemId)) {
            return false;
        }

        ItemRepository.itemIdSet.add(newItemId);
        ItemRepository.itemDataHashMap.put(newItemId, item);

        ItemRepository itemRepository = new ItemRepository();
        itemRepository.saveItemData(newItemId);
        itemRepository.saveItemIdSetData();

        return true;
    }

    @Override
    public boolean removeItem(int itemId) {

        if(!ItemRepository.itemIdSet.contains(itemId)) {
            return false;
        }
        ItemRepository itemRepository = new ItemRepository();

        ItemRepository.itemDataHashMap.remove(itemId);
        itemRepository.removeItemData(itemId);

        ItemRepository.itemIdSet.remove(itemId);
        itemRepository.saveItemIdSetData();

        return true;
    }

    @Override
    public int getNewItemId() {
        HashSet<Integer> itemIdSet = ItemRepository.itemIdSet;
        int newId = 0;
        for (int id : itemIdSet) {
            if(id > newId) newId = id;
        }
        return newId + 1;
    }
}
