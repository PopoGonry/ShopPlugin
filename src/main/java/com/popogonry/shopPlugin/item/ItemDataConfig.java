package com.popogonry.shopPlugin.item;

import com.popogonry.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemDataConfig extends Config {
    public ItemDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
        loadDefaults();
    }

    public void storeItemData(int itemId, Item item){
        getConfig().set(String.valueOf(itemId).toString(), item);
        super.store();
    }

    public Item loadItemData(int itemId){
        return (Item) getConfig().get(String.valueOf(itemId).toString());
    }

    public boolean hasItemData(int itemId){
        return getConfig().contains(String.valueOf(itemId));
    }

    public void removeItemData(int itemId){
        getConfig().set(String.valueOf(itemId), null);
    }

    public void storeItemIdSetData(Set<Integer> itemIdSet){
        getConfig().set("itemIdSet", new ArrayList<>(itemIdSet));
        super.store();
    }

    public Set<Integer> loadItemIdSetData(){
        return new HashSet<>(getConfig().getIntegerList("itemIdSet"));
    }

    public boolean hasItemIdSetData(){
        return getConfig().contains("itemIdSet");
    }

    @Override
    public void loadDefaults() {

    }

    @Override
    public void applySettings() {
        getConfig().options().copyDefaults(true);
    }
}