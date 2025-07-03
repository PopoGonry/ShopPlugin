
package com.popogonry.shopPlugin;

public class PluginDataConfig extends Config {
    public PluginDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
    }

    public PluginConfig loadPluginDataConfig() {
        return new PluginConfig(
                this.getConfig().getString("CashShop-GUI-Display-Name")
        );
    }

    public void loadDefaults() {
    }

    public void applySettings() {
        this.getConfig().options().copyDefaults(true);
    }
}