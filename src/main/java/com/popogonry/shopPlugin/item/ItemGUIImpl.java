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

import java.text.SimpleDateFormat;
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
    public boolean openItemSettingGUI(Player player, Integer itemID) {
        Item item = ItemRepository.itemDataHashMap.get(itemID);

        Inventory inventory = Bukkit.createInventory(player, 27, Reference.prefix_normal + "Item Setting GUI ID:" + itemID);

        inventory.setItem(4, getItemStackShopVer(itemID));
        inventory.setItem(10, GUI.getCustomItemStack(Material.NAME_TAG, ChatColor.GOLD + "Item Name", Collections.singletonList(item.getName())));
        inventory.setItem(11, GUI.getCustomItemStack(Material.BOOK, ChatColor.GOLD + "Item Lore", item.getLore()));
        inventory.setItem(12, GUI.getCustomItemStack(Material.EMERALD, ChatColor.GOLD + "Item Price", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getPrice()))));
        inventory.setItem(13, GUI.getCustomItemStack(Material.DIAMOND, ChatColor.GOLD + "Item Discount Price", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getDiscountPrice()))));
        inventory.setItem(14, GUI.getCustomItemStack(Material.APPLE, ChatColor.GOLD + "Item Remain Amount", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getRemainAmount()))));
        inventory.setItem(15, GUI.getCustomItemStack(Material.GOLDEN_APPLE, ChatColor.GOLD + "Item Limit Amount", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getLimitAmount()))));

        if(item.getIsLimitAmount()) {
            inventory.setItem(16, GUI.getCustomItemStack(Material.GREEN_CONCRETE, ChatColor.GREEN + "Limit Amount of Items", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsLimitAmount()))));
        }
        else {
            inventory.setItem(16, GUI.getCustomItemStack(Material.RED_CONCRETE, ChatColor.RED + "Limit Amount of Items", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsLimitAmount()))));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 원하는 형식 지정
        inventory.setItem(20, GUI.getCustomItemStack(Material.CLOCK, ChatColor.GOLD + "Item Limit Date", Collections.singletonList(ChatColor.WHITE + dateFormat.format(new Date(item.getLimitDate())))));

        if(item.getIsLimitDate()) {
            inventory.setItem(21, GUI.getCustomItemStack(Material.GREEN_CONCRETE, ChatColor.GREEN + "Limit Date of Items", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsLimitDate()))));
        }
        else {
            inventory.setItem(21, GUI.getCustomItemStack(Material.RED_CONCRETE, ChatColor.RED + "Limit Date of Items", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsLimitDate()))));
        }

        if(item.getIsUseItemLore()) {
            inventory.setItem(23, GUI.getCustomItemStack(Material.GREEN_CONCRETE, ChatColor.GREEN + "Use Item's Lore", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsUseItemLore()))));
        }
        else {
            inventory.setItem(23, GUI.getCustomItemStack(Material.RED_CONCRETE, ChatColor.RED + "Use Item's Lore", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsUseItemLore()))));

        }
        inventory.setItem(26, GUI.getCustomItemStack(Material.PAPER, ChatColor.GOLD + "Return to Item List"));


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
        lore.add(ChatColor.GREEN + "잔여 수량: " + ChatColor.YELLOW + item.getRemainAmount());
        lore.add(ChatColor.GREEN + "제한 수량: " + ChatColor.YELLOW + item.getLimitAmount());
        lore.add(ChatColor.GREEN + "제한 여부: " + (item.getIsLimitAmount() ? ChatColor.YELLOW + "예" : ChatColor.YELLOW + "아니오"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 원하는 형식 지정
        lore.add(ChatColor.GREEN + "제한 날짜: " + ChatColor.YELLOW + dateFormat.format(new Date(item.getLimitDate())));
        lore.add(ChatColor.GREEN + "날짜 제한 여부: " + (item.getIsLimitDate() ? ChatColor.YELLOW + "예" : ChatColor.YELLOW + "아니오"));
        lore.add(ChatColor.GREEN + "아이템 로어 사용 여부: " + (item.getIsUseItemLore() ? ChatColor.YELLOW + "예" : ChatColor.YELLOW + "아니오"));
        lore.add(ChatColor.GRAY + "식별 코드: " + itemID);

        lore.add("---------------------");
        lore.add(ChatColor.GOLD + "- 좌클릭: 아이템 설정 GUI");
        lore.add(ChatColor.GOLD + "- 쉬프트 + 우클릭: 아이템 제거");
        lore.add(ChatColor.GOLD + "- 플레이어 인벤토리 좌클릭: 아이템 추가");

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
        if(item.getIsUseItemLore()) {
            List<String> itemStackLore = item.getItemStack().getLore();
            if (itemStackLore != null) {
                lore.addAll(itemStackLore);
                lore.add(ChatColor.GOLD + "------------------");
            }
            else {
                lore.add(" ");
            }
        }
        else {
            lore.add(" ");
        }

        // 할인 X
        if(item.getPrice() == item.getDiscountPrice()) {
            lore.add(ChatColor.GOLD + "- 가격: " + item.getPrice());
        }
        // 할인 O
        else {
            lore.add(ChatColor.GOLD + "- 가격: " + ChatColor.RED + ChatColor.STRIKETHROUGH + item.getPrice() + "원" +
                    ChatColor.RESET + ChatColor.GOLD + " => " + ChatColor.GREEN + item.getDiscountPrice() + "원" +
                    ChatColor.GRAY + " (" + String.format("%.1f",
                    (item.getPrice() > 0 ? (double)(item.getPrice() - item.getDiscountPrice()) / item.getPrice() * 100 : 0)) + "%)");

        }

        // 남은 수량
        if(item.getIsLimitAmount()) {
            lore.add(ChatColor.GOLD + "- 남은 수량: " + item.getRemainAmount() + "/" + item.getLimitAmount() + "개");
        }

        // 판매 기간
        if(item.getIsLimitDate()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 원하는 형식 지정
            lore.add(ChatColor.GOLD + "- 판매 기간: " + dateFormat.format(new Date(item.getLimitDate())) + "까지");
        }


        // item.getLore()가 null이 아닐 때만 추가
        if (!item.getLore().isEmpty()) {
            lore.add(" ");
            lore.addAll(item.getLore());
        }


        returnItemMeta.setLore(lore);

        returnItemStack.setItemMeta(returnItemMeta);

        return returnItemStack;
    }
}
