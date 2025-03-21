package com.popogonry.shopPlugin.item;

import com.popogonry.shopPlugin.ShopPlugin;

import java.util.*;

public class ItemRepository {

    private static final String CONFIG_FILE_NAME = "item.yml";
    private final String configBasePath;

    private final ItemDataConfig itemDataConfig;

    public static HashMap<Integer, Item> itemDataHashMap = new HashMap<>();
    public static HashSet<Integer> itemIdSet = new HashSet<>();


    public ItemRepository() {
        this.configBasePath = ShopPlugin.getServerInstance().getDataFolder().getAbsolutePath();
        this.itemDataConfig = new ItemDataConfig(configBasePath, CONFIG_FILE_NAME);
    }

    public void reloadConfig() {
        itemDataConfig.reload();
    }

    public void saveConfig() {
        itemDataConfig.store();
    }

    public boolean hasItemData(int itemId) {
        return itemDataConfig.hasItemData(itemId);
    }

    public void storeItemData(int itemId) {
        itemDataConfig.storeItemData(itemId, itemDataHashMap.get(itemId));
        itemDataHashMap.remove(itemId);
    }
    public void saveItemData(int itemId) {
        itemDataConfig.storeItemData(itemId, itemDataHashMap.get(itemId));
    }

    public void loadItemData(int itemId) {
        itemDataHashMap.put(itemId, itemDataConfig.loadItemData(itemId));
    }

    public boolean hasItemIdSetData() {
        return itemDataConfig.hasItemIdSetData();
    }
    public void removeItemData(int itemId) {
        itemDataConfig.removeItemData(itemId);
    }

    public void storeItemIdSetData() {
        itemDataConfig.storeItemIdSetData(itemIdSet);
        itemIdSet = null;
    }
    public void saveItemIdSetData() {
        itemDataConfig.storeItemIdSetData(itemIdSet);
    }

    public void loadItemIdSetData() {
        itemIdSet = (HashSet<Integer>) itemDataConfig.loadItemIdSetData();
        if(itemIdSet == null) {
            itemIdSet = new HashSet<>();
        }
    }

}
