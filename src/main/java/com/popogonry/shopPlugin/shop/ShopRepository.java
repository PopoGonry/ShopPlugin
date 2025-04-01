package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.ShopPlugin;
import com.popogonry.shopPlugin.item.Item;
import com.popogonry.shopPlugin.item.ItemDataConfig;
import com.popogonry.shopPlugin.item.ItemRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
        Shop shop = shopDataConfig.loadShopData(shopName);
        HashMap<Integer, Integer> shopItemHashMap = shop.getItemHashMap();

        List<Integer> keys = new ArrayList<>(shopItemHashMap.keySet());

        for (Integer i : keys) {
            if(!ItemRepository.itemIdSet.contains(shopItemHashMap.get(i))) {
                shopItemHashMap.remove(i);
            }
        }
        shop.setItemHashMap(shopItemHashMap);

        shopDataHashMap.put(shopName, shop);

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
