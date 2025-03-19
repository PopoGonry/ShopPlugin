package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShopDataConfig extends Config {


    public ShopDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
        loadDefaults();
    }

    public void storeShopData(Shop shop){
        getConfig().set(shop.getName(), shop);
        super.store();
    }

    public Shop loadShopData(String shopName){
        return (Shop) getConfig().get(shopName);
    }

    public boolean hasShopData(String shopName){
        return getConfig().contains(shopName);
    }

    public void removeShopData(String shopName){
        getConfig().set(shopName, null);
    }

    public void storeShopNameSetData(Set<String> shopNameSet){
        getConfig().set("shopNameSet", new ArrayList<>(shopNameSet));
        super.store();
    }

    public HashSet<String> loadShopNameSetData(){
        return new HashSet<>(getConfig().getStringList("shopNameSet"));
    }

    public boolean hasShopNameSetData(){
        return getConfig().contains("shopNameSet");
    }

    @Override
    public void loadDefaults() {

    }

    @Override
    public void applySettings() {

    }
}
