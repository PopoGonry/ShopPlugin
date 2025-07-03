package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.GUI;
import com.popogonry.shopPlugin.Reference;
import com.popogonry.shopPlugin.ShopPluginRepository;
import com.popogonry.shopPlugin.item.Item;
import com.popogonry.shopPlugin.item.ItemGUI;
import com.popogonry.shopPlugin.item.ItemGUIImpl;
import com.popogonry.shopPlugin.item.ItemRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ShopGUIImpl implements ShopGUI {
    @Override
    public boolean openShopListGUI(Player player, int page) {
        Inventory inventory = Bukkit.createInventory(player, 54, Reference.prefix_normal + "Shop List GUI");

        ArrayList<String> shopNameList = new ArrayList<>(ShopRepository.shopNameSet);
        Collections.sort(shopNameList);

        for (int i = 0 + (45*(page-1)); i < 45 + (45*(page-1)) && i < shopNameList.size(); i++) {
            ItemStack itemStack = new ItemStack(Material.CHEST);
            ItemMeta itemMeta = itemStack.getItemMeta();
            Shop shop = ShopRepository.shopDataHashMap.get(shopNameList.get(i));

            itemMeta.setDisplayName(shop.getName());

            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.GOLD + "상점 이름: " + shop.getName());
            lore.add(ChatColor.GOLD + "상점 아이템 갯수: " + shop.getItemHashMap().size());
            lore.add(ChatColor.GOLD + "상점 크기: " + shop.getSize());

            lore.add(ChatColor.WHITE + "---------------------");
            lore.add(ChatColor.GOLD + "- 좌클릭: 상점 설정 GUI");
            lore.add(ChatColor.GOLD + "- 쉬프트 + 우클릭: 상점 제거");

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            inventory.addItem(itemStack);
        }


        // 48 49 50
        int maxPage = shopNameList.size() / 45;
        maxPage += shopNameList.size() % 45 == 0 ? 0 : 1;

        inventory.setItem(49, GUI.getCustomItemStack(Material.EMERALD, Reference.prefix + "Page " + page + " / " + maxPage, Collections.singletonList(ChatColor.GOLD + "Amount of Shops: " + shopNameList.size())));

        if(page > 1) {
            inventory.setItem(48, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page - 1)));
        }

        if(page < maxPage) {
            inventory.setItem(50, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page + 1)));
        }

        inventory.setItem(53, GUI.getCustomItemStack(Material.OAK_SIGN, Reference.prefix + "Create Shop"));

        player.openInventory(inventory);

        return true;
    }

    @Override
    public boolean openShopSettingGUI(Player player, String shopName) {
        Shop shop = ShopRepository.shopDataHashMap.get(shopName);

        Inventory inventory = Bukkit.createInventory(player, 27, Reference.prefix_normal + "Shop Setting GUI");

        inventory.setItem(11, GUI.getCustomItemStack(Material.NAME_TAG, ChatColor.GOLD + "Shop Name", Collections.singletonList(ChatColor.WHITE + shop.getName())));
        inventory.setItem(13, GUI.getCustomItemStack(Material.OAK_SIGN, ChatColor.GOLD + "Shop Item Setting", Collections.singletonList(ChatColor.WHITE + "Amount of Items: " + shop.getItemHashMap().size())));
        inventory.setItem(15, GUI.getCustomItemStack(Material.CHEST, ChatColor.GOLD + "Shop Size", Collections.singletonList(ChatColor.WHITE + String.valueOf(shop.getSize()))));

        inventory.setItem(26, GUI.getCustomItemStack(Material.PAPER, ChatColor.GOLD + "Return to Shop List"));


        player.openInventory(inventory);

        return true;
    }

    @Override
    public boolean openShopItemSettingGUI(Player player, String shopName, int page) {
        Inventory inventory = Bukkit.createInventory(player, 54, Reference.prefix_normal + "Shop Item Setting GUI");

        Shop shop = ShopRepository.shopDataHashMap.get(shopName);

        ItemGUI itemGUI = new ItemGUIImpl();

        for(int i = 0; i < 45; i++) {
            if(shop.getItemHashMap().containsKey(i + 45 *(page-1))) {
                int itemID = shop.getItemHashMap().get(i + 45 *(page-1));

                if(ItemRepository.itemIdSet.contains(itemID)) {
                    inventory.setItem(i, itemGUI.getItemStackShopVer(itemID));
                }
                else {
                    shop.getItemHashMap().remove(i + 45 *(page-1));
                }
            }
        }

        inventory.setItem(49, GUI.getCustomItemStack(Material.EMERALD, Reference.prefix + "Page " + page + " / " + shop.getSize(), Collections.singletonList(ChatColor.GOLD + "Amount of items: " + shop.getItemHashMap().size())));

        if(page > 1) {
            inventory.setItem(48, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page - 1)));
        }

        if(page < shop.getSize()) {
            inventory.setItem(50, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page + 1)));
        }

        inventory.setItem(53, GUI.getCustomItemStack(Material.PAPER, ChatColor.GOLD + "Return to Shop Setting"));


        player.openInventory(inventory);

        return true;
    }

    @Override
    public boolean openItemListtoAddGUI(Player player, int page, String shopName, int slot) {
        Inventory inventory = Bukkit.createInventory(player, 54, Reference.prefix_normal + "Item List to Add GUI");

        List<Integer> itemIDList = new ArrayList<>(ItemRepository.itemIdSet);
        Collections.sort(itemIDList);

        ItemGUI itemGUI = new ItemGUIImpl();

        for (int i = 0 + (45*(page-1)); i < 45 + (45*(page-1)) && i < itemIDList.size(); i++) {
            inventory.addItem(itemGUI.getItemStack(itemIDList.get(i)));
        }

        // 48 49 50
        int maxPage = itemIDList.size() / 45;
        maxPage += itemIDList.size() % 45 == 0 ? 0 : 1;


        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Amount of Items: " + itemIDList.size());
        lore.add(ChatColor.GOLD + "Shop Name: " + shopName);
        lore.add(ChatColor.GOLD + "Slot: " + slot);

        inventory.setItem(49, GUI.getCustomItemStack(Material.EMERALD, Reference.prefix + "Page " + page + " / " + maxPage, lore));

        if(page > 1) {
            inventory.setItem(48, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page - 1)));
        }

        if(page < maxPage) {
            inventory.setItem(50, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page + 1)));
        }

        inventory.setItem(53, GUI.getCustomItemStack(Material.PAPER, ChatColor.GOLD + "Return to Shop Item Setting"));

        player.openInventory(inventory);

        return true;
    }


    // 찐 상점 GUI
    @Override
    public boolean openShopGUI(Player player, String shopName, int page) {
        Inventory inventory = Bukkit.createInventory(player, 54, ShopPluginRepository.pluginConfig.getCashShopGUIDisplayName());

        Shop shop = ShopRepository.shopDataHashMap.get(shopName);

        ItemGUI itemGUI = new ItemGUIImpl();

        for(int i = 0; i < 45; i++) {
            if(shop.getItemHashMap().containsKey(i + 45 *(page-1))) {
                int itemID = shop.getItemHashMap().get(i + 45 *(page-1));

                if(ItemRepository.itemIdSet.contains(itemID)) {
                    inventory.setItem(i, itemGUI.getItemStackShopVer(itemID));
                }
                else {
                    shop.getItemHashMap().remove(i + 45 *(page-1));
                }
            }
        }

        inventory.setItem(49, GUI.getCustomItemStack(Material.EMERALD, Reference.prefix_normal + shop.getName(), Collections.singletonList(ChatColor.WHITE + "- Page " + page + " / " + shop.getSize())));

        if(page > 1) {
            inventory.setItem(48, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page - 1)));
        }

        if(page < shop.getSize()) {
            inventory.setItem(50, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page + 1)));
        }

        player.openInventory(inventory);

        return true;
    }
}
