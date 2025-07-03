
package com.popogonry.shopPlugin;

import java.util.List;

public class PluginConfig {
    private final String cashShopGUIDisplayName;

    public PluginConfig(String cashShopGUIDisplayName) {
        this.cashShopGUIDisplayName = cashShopGUIDisplayName;
    }

    public String getCashShopGUIDisplayName() {
        return cashShopGUIDisplayName;
    }

    @Override
    public String toString() {
        return "PluginConfig{" +
                "cashShopGUIDisplayName='" + cashShopGUIDisplayName + '\'' +
                '}';
    }
}
