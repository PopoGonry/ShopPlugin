package com.popogonry.shopPlugin.cash;

import com.popogonry.shopPlugin.ShopPlugin;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;

public class CashRepository {

    private static final String CONFIG_FILE_NAME = "cash.yml";
    private final String configBasePath;

    private final CashDataConfig cashDataConfig;

    static HashMap<UUID, Integer> cashDataHashMap = new HashMap<UUID, Integer>();


    public CashRepository() {
        configBasePath = ShopPlugin.getServerInstance().getDataFolder().getAbsolutePath();
        cashDataConfig = new CashDataConfig(configBasePath,CONFIG_FILE_NAME);
    }

    public void reloadConfig() {
        cashDataConfig.reload();
    }

    public void saveConfig() {
        cashDataConfig.store();
    }

    public boolean hasData(UUID uuid) {
        return cashDataConfig.hasCashData(uuid);
    }

    public void storeCashData(UUID uuid) {
        cashDataConfig.storeCashData(uuid, cashDataHashMap.getOrDefault(uuid, 0));
        cashDataHashMap.remove(uuid);
    }

    public void saveCashData(UUID uuid) {
        cashDataConfig.storeCashData(uuid, cashDataHashMap.getOrDefault(uuid, 0));
    }

    public void loadCashData(UUID uuid) {
        cashDataHashMap.put(uuid, cashDataConfig.loadCashData(uuid));
    }

}
