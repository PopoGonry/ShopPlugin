package com.popogonry.shopPlugin.cash;

import com.popogonry.Config;

import java.util.UUID;

public class CashDataConfig extends Config {

    public CashDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
        loadDefaults();
    }

    public void storeCashData(UUID uuid, int cash) {
        getConfig().set(uuid.toString(), cash);
        super.store();
    }

    public int loadCashData(UUID uuid) {
        return getConfig().getInt(uuid.toString());
    }

    public boolean hasCashData(UUID uuid) {
        return getConfig().contains(uuid.toString());
    }

    @Override
    public void loadDefaults() {

    }

    @Override
    public void applySettings() {
        getConfig().options().copyDefaults(true);
    }
}
