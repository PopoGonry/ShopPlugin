package com.popogonry.shopPlugin.item;

import com.popogonry.shopPlugin.GUI;
import com.popogonry.shopPlugin.Reference;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemGUIImpl implements ItemGUI {
    @Override
    public boolean openItemListGUI(Player player, int page) {
        Inventory inventory = Bukkit.createInventory(player, 54, Reference.prefix_normal + "Item List GUI");

        List<Integer> itemIDList = new ArrayList<>(ItemRepository.itemIdSet);
        Collections.sort(itemIDList);

        for (int i = 0 + (45*(page-1)); i < 45 + (45*(page-1)) && i < itemIDList.size(); i++) {
            inventory.addItem(getItemStack(itemIDList.get(i)));
        }

        // 48 49 50
        int maxPage = itemIDList.size() / 45;
        maxPage += itemIDList.size() % 45 == 0 ? 0 : 1;

        inventory.setItem(49, GUI.getCustomItemStack(Material.EMERALD, Reference.prefix + "Page " + page + " / " + maxPage, Collections.singletonList(ChatColor.GOLD + "Amount of Items: " + itemIDList.size())));

        if(page > 1) {
            inventory.setItem(48, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page - 1)));
        }

        if(page < maxPage) {
            inventory.setItem(50, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page + 1)));
        }

        player.openInventory(inventory);

        return true;
    }

    @Override
    public boolean openItemSettingGUI(Player player, Item item) {
        Inventory inventory = Bukkit.createInventory(player, 27, Reference.prefix_normal + item.getName() + " Setting GUI");




        player.openInventory(inventory);

        return true;
    }


    @Override
    public ItemStack getItemStack(Integer itemID) {
        Item item = ItemRepository.itemDataHashMap.get(itemID);

        ItemStack returnItemStack = new ItemStack(item.getItemStack().getType());
        ItemMeta returnItemMeta = returnItemStack.getItemMeta();

        returnItemMeta.setDisplayName(item.getName());

        List<String> lore = item.getItemStack().getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        // item.getLore()가 null이 아닐 때만 추가
        if (item.getLore() != null) {
            lore.add("---- Lore ----");
            lore.addAll(item.getLore());
        }

        // 정보 추가 (색상 포함)
        lore.add(ChatColor.GREEN + "가격: " + ChatColor.YELLOW + item.getPrice());
        lore.add(ChatColor.GREEN + "할인 가격: " + ChatColor.YELLOW + item.getDiscountPrice());
        lore.add(ChatColor.GREEN + "남은 수량: " + ChatColor.YELLOW + item.getRemainAmount());
        lore.add(ChatColor.GREEN + "제한 수량: " + ChatColor.YELLOW + item.getLimitAmount());
        lore.add(ChatColor.GREEN + "제한 여부: " + (item.isLimitAmount() ? ChatColor.YELLOW + "예" : ChatColor.YELLOW + "아니오"));
        lore.add(ChatColor.GREEN + "제한 날짜: " + ChatColor.YELLOW + item.getLimitDate());
        lore.add(ChatColor.GREEN + "날짜 제한 여부: " + (item.isLimitDate() ? ChatColor.YELLOW + "예" : ChatColor.YELLOW + "아니오"));
        lore.add(ChatColor.GREEN + "아이템 로어 사용 여부: " + (item.isUseItemLore() ? ChatColor.YELLOW + "예" : ChatColor.YELLOW + "아니오"));
        lore.add(ChatColor.GRAY + "식별 코드: " + itemID);

        returnItemMeta.setLore(lore);
        returnItemStack.setItemMeta(returnItemMeta);

        return returnItemStack;
    }

    @Override
    public ItemStack getItemStackShopVer(Integer itemID) {
        Item item = ItemRepository.itemDataHashMap.get(itemID);

        ItemStack returnItemStack = new ItemStack(item.getItemStack().getType());
        ItemMeta returnItemMeta = returnItemStack.getItemMeta();

        // 이름 세팅
        returnItemMeta.setDisplayName(item.getName());


        // 전체 로어 생성
        List<String> lore = new ArrayList<>();

        // 아이템스택 로어 추가
        if(item.isLimitAmount()) {
            List<String> itemStackLore = item.getItemStack().getLore();
            if (itemStackLore != null) {
                lore.addAll(itemStackLore);
            }
            lore.add("------------------");
        }

        // 할인 X
        if(item.getPrice() == item.getDiscountPrice()) {

        }
        // 할인 O
        else {

        }



        // item.getLore()가 null이 아닐 때만 추가
        if (item.getLore() != null) {
            lore.add("---- Lore ----");
            lore.addAll(item.getLore());
        }


        return null;
    }
}
