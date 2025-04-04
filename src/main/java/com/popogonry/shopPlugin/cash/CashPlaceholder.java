package com.popogonry.shopPlugin.cash;

import com.popogonry.shopPlugin.ShopPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CashPlaceholder extends PlaceholderExpansion {

    private final ShopPlugin plugin;

    public CashPlaceholder(ShopPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "cashShop";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PopoGonry";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("cash")) {
            return String.valueOf(CashRepository.cashDataHashMap.get(player.getUniqueId()));
        }

        return null; // 정의되지 않은 플레이스홀더 처리
    }
}
