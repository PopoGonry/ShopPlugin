package com.popogonry.shopPlugin.shop;

public interface ShopService {

    boolean createShop(Shop shop);
    boolean updateShop(String shopName, Shop shop);
    boolean removeShop(String shopName);

}
