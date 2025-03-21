package com.popogonry.shopPlugin.shop;

public class ShopServiceImpl implements ShopService {

    @Override
    public boolean createShop(Shop shop) {
        if(ShopRepository.shopNameSet.contains(shop.getName())) {
            return false;
        }

        ShopRepository.shopNameSet.add(shop.getName());
        ShopRepository.shopDataHashMap.put(shop.getName(), shop);


        ShopRepository shopRepository = new ShopRepository();

        shopRepository.saveShopData(shop.getName());
        shopRepository.saveShopNameSetData();

        return true;
    }

    @Override
    public boolean updateShop(String shopName, Shop shop) {
        ShopRepository.shopNameSet.add(shop.getName());
        ShopRepository.shopDataHashMap.put(shop.getName(), shop);

        ShopRepository shopRepository = new ShopRepository();

        shopRepository.saveShopData(shop.getName());
        shopRepository.saveShopNameSetData();

        return true;
    }

    @Override
    public boolean removeShop(String shopName) {

        if(!ShopRepository.shopNameSet.contains(shopName)) {
            return false;
        }

        ShopRepository.shopDataHashMap.remove(shopName);
        ShopRepository.shopNameSet.remove(shopName);

        ShopRepository shopRepository = new ShopRepository();
        shopRepository.removeShopData(shopName);
        shopRepository.saveShopData(shopName);
        return true;
    }
}
