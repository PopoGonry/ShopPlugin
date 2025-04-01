package com.popogonry.shopPlugin.item;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


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
    private int limitAmount;
    private boolean isLimitAmount;

    private long limitDate;
    private Boolean isLimitDate;

    private boolean isUseItemLore;

    public Item(ItemStack itemStack, String name, List<String> lore, int price, int discountPrice, int remainAmount, int limitAmount, boolean isLimitAmount, Long limitDate, Boolean isLimitDate, boolean isUseItemLore) {
        this.itemStack = itemStack;
        this.name = name;
        this.lore = lore;
        this.price = price;
        this.discountPrice = discountPrice;
        this.remainAmount = remainAmount;
        this.limitAmount = limitAmount;
        this.isLimitAmount = isLimitAmount;
        this.limitDate = limitDate;
        this.isLimitDate = isLimitDate;
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
        map.put("limitAmount", limitAmount);
        map.put("isLimitAmount", isLimitAmount);
        map.put("limitDate", limitDate);
        map.put("isLimitDate", isLimitDate);
        map.put("isUseItemLore", isUseItemLore);

        return map;

    }

    public static Item deserialize(Map<String, Object> map) {
        return new Item (
                ItemStack.deserialize((Map<String, Object>) map.get("itemStack")),
                (String) map.get("name"),
                (List<String>) map.get("lore"),
                (int) map.get("price"),
                (Integer) map.get("discountPrice"),
                (Integer) map.get("remainAmount"),
                (Integer) map.get("limitAmount"),
                (boolean) map.get("isLimitAmount"),
                Long.valueOf(String.valueOf(map.get("limitDate"))),
                (boolean) map.get("isLimitDate"),
                (boolean) map.get("isUseItemLore")
        );
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
                ", limitAmount=" + limitAmount +
                ", isLimitAmount=" + isLimitAmount +
                ", limitDate=" + limitDate +
                ", isLimitDate=" + isLimitDate +
                ", isUseItemLore=" + isUseItemLore +
                '}';
    }

    public ItemStack getItemStack() {
        return new ItemStack(itemStack);
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

    public int getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(int limitAmount) {
        this.limitAmount = limitAmount;
    }

    public boolean getIsLimitAmount() {
        return isLimitAmount;
    }


    public boolean getIsLimitDate() {
        return isLimitDate;
    }


    public long getLimitDate() {
        return limitDate;
    }


    public boolean getIsUseItemLore() {
        return isUseItemLore;
    }


    public void setLimitDate(long limitDate) {
        this.limitDate = limitDate;
    }

    public void setIsLimitAmount(boolean limitAmount) {
        isLimitAmount = limitAmount;
    }

    public void setIsLimitDate(Boolean limitDate) {
        isLimitDate = limitDate;
    }

    public void setIsUseItemLore(boolean useItemLore) {
        isUseItemLore = useItemLore;
    }
}
