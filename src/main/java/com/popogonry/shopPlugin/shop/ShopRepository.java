package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.ShopPlugin;
import com.popogonry.shopPlugin.item.Item;
import com.popogonry.shopPlugin.item.ItemDataConfig;

import java.util.HashMap;
import java.util.HashSet;

public class ShopRepository {

    private static final String CONFIG_FILE_NAME = "shop.yml";
    private final String configBasePath;

    private final ShopDataConfig shopDataConfig;

    static HashMap<String, Shop> shopDataHashMap = new HashMap<>();
    public static HashSet<String> shopNameSet = new HashSet<>();


    public ShopRepository() {
        this.configBasePath = ShopPlugin.getServerInstance().getDataFolder().getAbsolutePath();
        this.shopDataConfig = new ShopDataConfig(configBasePath, CONFIG_FILE_NAME);
    }

    public void reloadConfig() {
        shopDataConfig.reload();
    }

    public void saveConfig() {
        shopDataConfig.store();
    }

    public boolean hasShopData(String shopName) {
        return shopDataConfig.hasShopData(shopName);
    }

    public void storeShopData(String shopName) {
        shopDataConfig.storeShopData(shopDataHashMap.get(shopName));
        shopDataHashMap.remove(shopName);
    }
    public void saveShopData(String shopName) {
        shopDataConfig.storeShopData(shopDataHashMap.get(shopName));
    }

    public void loadShopData(String shopName) {
        shopDataHashMap.put(shopName, shopDataConfig.loadShopData(shopName));
    }
    public void removeShopData(String shopName) {
        shopDataConfig.removeShopData(shopName);
    }

    public boolean hasShopNameSetData() {
        return shopDataConfig.hasShopNameSetData();
    }

    public void storeShopNameSetData() {
        shopDataConfig.storeShopNameSetData(shopNameSet);
        shopNameSet = null;
    }
    public void saveShopNameSetData() {
        shopDataConfig.storeShopNameSetData(shopNameSet);
    }

    public void loadShopNameSetData() {
        shopNameSet = shopDataConfig.loadShopNameSetData();
        if(shopNameSet == null) {
            shopNameSet = new HashSet<>();
        }
    }

}
