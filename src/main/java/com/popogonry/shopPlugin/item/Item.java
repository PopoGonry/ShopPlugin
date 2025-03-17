package com.popogonry.shopPlugin.item;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item implements ConfigurationSerializable {
    private ItemStack itemStack;
    private String name;
    private List<String> lore;
    private int price;
    private int discountPrice;
    private int remainAmount;
    private boolean isUseItemLore;

    public Item(ItemStack itemStack, String name, List<String> lore, int price, Integer discountPrice, Integer remainAmount, boolean isUseItemLore) {
        this.itemStack = itemStack;
        this.name = name;
        this.lore = lore;
        this.price = price;
        this.discountPrice = discountPrice;
        this.remainAmount = remainAmount;
        this.isUseItemLore = isUseItemLore;

    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("itemStack", itemStack.serialize());
        map.put("name", name);
        map.put("lore", lore);
        map.put("price", price);
        map.put("discountPrice", discountPrice);
        map.put("remainAmount", remainAmount);
        map.put("isUseItemLore", isUseItemLore);

        return map;

    }

    public static Item deserialize(Map<String, Object> map) {
        return new Item(
                ItemStack.deserialize((Map<String, Object>) map.get("itemStack")),
                (String) map.get("name"),
                (List<String>) map.get("lore"),
                (int) map.get("price"),
                (Integer) map.get("discountPrice"),
                (Integer) map.get("remainAmount"),
                (boolean) map.get("isUseItemLore"));
    }


    @Override
    public String toString() {
        return "Item{" +
                "itemStack=" + itemStack +
                ", name='" + name + '\'' +
                ", lore=" + lore +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", remainAmount=" + remainAmount +
                ", isUseItemLore=" + isUseItemLore +
                '}';
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(int remainAmount) {
        this.remainAmount = remainAmount;
    }

    public boolean isUseItemLore() {
        return isUseItemLore;
    }

    public void setUseItemLore(boolean useItemLore) {
        isUseItemLore = useItemLore;
    }


}
