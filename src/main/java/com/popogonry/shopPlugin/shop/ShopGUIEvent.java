package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.Reference;
import com.popogonry.shopPlugin.ShopPlugin;
import com.popogonry.shopPlugin.ShopPluginRepository;
import com.popogonry.shopPlugin.item.*;
import it.unimi.dsi.fastutil.Hash;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ShopGUIEvent implements Listener {

    @EventHandler
    public static void onClickShopListGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(Reference.prefix_normal + "Shop List GUI")
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType() != Material.AIR) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            int slot = event.getRawSlot();


            ShopGUI shopGUI = new ShopGUIImpl();
            ShopService shopService = new ShopServiceImpl();

            String[] strings1 = inventory.getItem(49).getItemMeta().getDisplayName().split("/");
            String[] strings2 = strings1[0].split(" ");
            int page = Integer.parseInt(strings2[1].replaceAll(" ", ""));

            // Item List
            if(0 <= slot && slot <= 44) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(event.getClick().isLeftClick()) {
                    shopGUI.openShopSettingGUI(player, itemMeta.getDisplayName());
                }
                else if(event.getClick().isRightClick() && event.getClick().isShiftClick()) {
                    shopService.removeShop(itemMeta.getDisplayName());
                    player.sendMessage(Reference.prefix_normal + itemMeta.getDisplayName() + " 상점이 제거 되었습니다.");
                     shopGUI.openShopListGUI(player, page);
                }


            }

            else if(48 <= slot && slot <= 50) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.getDisplayName().contains("To")) {
                    String[] strings = itemMeta.getDisplayName().split(" ");
                    shopGUI.openShopListGUI(player, Integer.parseInt(strings[1]));
                }
            }

            // Create Shop
            else if(slot == 53) {
                ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ShopInputMode.ShopCreateName);
                player.closeInventory();
                player.sendMessage(Reference.prefix_normal + "이름을 입력 해주세요. ( 취소: -c )");
            }

            // Player Inventory
            else if(54 <= slot && slot <= 89) {

            }

        }
    }

    @EventHandler
    public static void onClickShopSettingGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(Reference.prefix_normal + "Shop Setting GUI")
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType() != Material.AIR) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            int slot = event.getRawSlot();

            ShopGUI shopGUI = new ShopGUIImpl();
            ShopService shopService = new ShopServiceImpl();

            Shop shop = ShopRepository.shopDataHashMap.get(inventory.getItem(11).getItemMeta().getLore().get(0).replaceAll("§f", ""));
            ShopPluginRepository.playerCurrentInventoryHashMap.put(player.getUniqueId(), inventory);


            switch (slot) {
                // Shop Name
                case 11:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ShopInputMode.ShopName);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "이름을 입력 해주세요. ( 취소: -c )");
                    break;


                //
                case 13:
                    shopGUI.openShopItemSettingGUI(player, shop.getName(), 1);
                    break;


                case 15:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ShopInputMode.ShopSize);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "크기를 입력 해주세요. ( 취소: -c )");
                    break;


                case 26:
                    shopGUI.openShopListGUI(player, 1);
                    break;
            }

        }
    }

    @EventHandler
    public static void onClickShopItemSettingGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(Reference.prefix_normal + "Shop Item Setting GUI")) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            int slot = event.getRawSlot();


            ShopGUI shopGUI = new ShopGUIImpl();
            ShopService shopService = new ShopServiceImpl();

            Shop shop = ShopRepository.shopDataHashMap.get(ShopPluginRepository.playerCurrentInventoryHashMap.get(player.getUniqueId()).getItem(11).getItemMeta().getLore().get(0).replaceAll("§f", ""));


            // Item List
            // 좌 클릭 아이템 배치, 아이템 리스트로 / 우클릭 아이템 세팅으로

            String[] strings1 = inventory.getItem(49).getItemMeta().getDisplayName().split("/");
            String[] strings2 = strings1[0].split(" ");
            int page = Integer.parseInt(strings2[1].replaceAll(" ", ""));

            if(0 <= slot && slot <= 44) {
                if(event.getClick().isLeftClick()) {
                    shopGUI.openItemListtoAddGUI(player, 1, shop.getName(), slot + ((page - 1) * 45));
                }
                else if(event.getClick().isRightClick()) {
                    HashMap<Integer, Integer> shopItemHashMap = shop.getItemHashMap();
                    shopItemHashMap.remove(slot + ((page - 1) * 45));
                    shop.setItemHashMap(shopItemHashMap);
                    shopGUI.openShopItemSettingGUI(player, shop.getName(), page);
                }
            }
            else if(48 <= slot && slot <= 50) {
                if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                    ItemStack itemStack = inventory.getItem(slot);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta.getDisplayName().contains("To")) {
                        String[] strings = itemMeta.getDisplayName().split(" ");
                        shopGUI.openShopItemSettingGUI(player, shop.getName(), Integer.parseInt(strings[1]));
                    }
                }
            }

            // Return
            else if(slot == 53) {
                shopGUI.openShopSettingGUI(player, shop.getName());
            }

            // Player Inventory
            else if(54 <= slot && slot <= 89) {

            }

        }
    }

    @EventHandler
    public static void onClickItemListtoAddGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(Reference.prefix_normal + "Item List to Add GUI")
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType() != Material.AIR) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            int slot = event.getRawSlot();

            ShopGUI shopGUI = new ShopGUIImpl();
            ItemService itemService = new ItemServiceImpl();

            String shopName = inventory.getItem(49).getItemMeta().getLore().get(1).split(": ")[1];
            int shopSlot = Integer.parseInt(inventory.getItem(49).getItemMeta().getLore().get(2).split(": ")[1]);

            Shop shop = ShopRepository.shopDataHashMap.get(shopName);

            int page = (shopSlot+1) / 45;
            page += (shopSlot+1) % 45 == 0 ? 0 : 1;
            // Item List
            if(0 <= slot && slot <= 44) {
                if(event.getClick().isLeftClick()) {
                    ItemStack itemStack = inventory.getItem(slot);
                    ItemMeta itemMeta = itemStack.getItemMeta();

                    // Find Item Id
                    int itemID = 0;
                    for (String lore : itemMeta.getLore()) {
                        if (lore.contains("식별 코드")) {
                            String[] strings = lore.split(":");
                            itemID = Integer.parseInt(strings[1].replaceAll(" ", ""));
                        }
                    }

                    if(!ItemRepository.itemDataHashMap.containsKey(itemID)) {
//                    player.sendMessage("not contains key");
                        return;
                    }

                    HashMap<Integer, Integer> shopItemHashMap = shop.getItemHashMap();
                    shopItemHashMap.put(shopSlot, itemID);
                    shop.setItemHashMap(shopItemHashMap);
                    shopGUI.openShopItemSettingGUI(player, shopName, page);
                }


            }

            else if(48 <= slot && slot <= 50) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.getDisplayName().contains("To")) {
                    String[] strings = itemMeta.getDisplayName().split(" ");
                    shopGUI.openItemListtoAddGUI(player, Integer.parseInt(strings[1]), shopName, shopSlot);
                }
            }

            // Return
            else if(slot == 53) {
                shopGUI.openShopItemSettingGUI(player, shopName, page);
            }
        }
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(ShopPluginRepository.playerInputModeHashMap.getOrDefault(event.getPlayer().getUniqueId(), null) instanceof ShopInputMode) {
            event.setCancelled(true);

            Player player = event.getPlayer();

            ShopInputMode mode = (ShopInputMode) ShopPluginRepository.playerInputModeHashMap.get(player.getUniqueId());

            ShopGUI shopGUI = new ShopGUIImpl();
            ShopService shopService = new ShopServiceImpl();

            if (mode == ShopInputMode.ShopCreateName) {
                if(event.getMessage().equalsIgnoreCase("-c")) {
                    ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                    ShopPluginRepository.playerCurrentInventoryHashMap.remove(player.getUniqueId());

                    Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                        shopGUI.openShopListGUI(player, 1);
                    });
                }
                else {
                    if(shopService.createShop(new Shop(event.getMessage(), new HashMap<>(), 1))) {
                        player.sendMessage(Reference.prefix_normal + event.getMessage() + " 상점이 생성 되었습니다.");

                        ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                        ShopPluginRepository.playerCurrentInventoryHashMap.remove(player.getUniqueId());
                        Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                            shopGUI.openShopSettingGUI(player, event.getMessage());
                        });
                    }
                    else {
                        player.sendMessage(Reference.prefix_normal + "이미 존재하는 상점 이름입니다.");
                    }
                }
            }
            else {
                Shop shop = ShopRepository.shopDataHashMap.get(ShopPluginRepository.playerCurrentInventoryHashMap.get(player.getUniqueId()).getItem(11).getItemMeta().getLore().get(0).replaceAll("§f", ""));

                if(event.getMessage().equalsIgnoreCase("-c")) {
                    ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                    ShopPluginRepository.playerCurrentInventoryHashMap.remove(player.getUniqueId());

                    Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                        shopGUI.openShopSettingGUI(player, shop.getName());
                    });

                    return;
                }

                else if(StringUtils.isNumeric(event.getMessage()) && mode != ShopInputMode.ShopName && mode != ShopInputMode.ShopCreateName) {
                    int value = Integer.parseInt(event.getMessage());

                    switch(mode) {
                        case ShopSize:
                            shop.setSize(value);
                            player.sendMessage(Reference.prefix_normal + shop.getName() + " 상점의 크기가 " + shop.getSize() + "로 변경 되었습니다.");

                            ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                            ShopPluginRepository.playerCurrentInventoryHashMap.remove(player.getUniqueId());

                            Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                                shopGUI.openShopSettingGUI(player, shop.getName());
                            });
                            break;

                        default:
                            player.sendMessage(Reference.prefix_normal + "잘못된 입력입니다.");
                            break;


                    }
                }
                else {
                    String message = event.getMessage();

                    switch(mode) {
                        case ShopName:
                            if(shopService.createShop(new Shop(message, shop.getItemHashMap(), shop.getSize()))) {
                                player.sendMessage(Reference.prefix_normal + shop.getName() + " 상점의 이름이 " + message + "로 변경 되었습니다.");

                                shopService.removeShop(shop.getName());

                                ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                                ShopPluginRepository.playerCurrentInventoryHashMap.remove(player.getUniqueId());
                                Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                                    shopGUI.openShopSettingGUI(player, message);
                                });
                            }
                            else {
                                player.sendMessage(Reference.prefix_normal + "이미 존재하는 상점 이름입니다.");
                            }
                            break;

                        default:
                            player.sendMessage(Reference.prefix_normal + "잘못된 입력입니다.");
                            break;
                    }
                }
            }
        }
    }




}
