package com.popogonry.shopPlugin.item;

import com.popogonry.shopPlugin.*;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ItemGUIEvent implements Listener {

    @EventHandler
    public static void onClickItemListGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(Reference.prefix_normal + "Item List GUI")
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType() != Material.AIR) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            int slot = event.getRawSlot();

            ItemGUI itemGUI = new ItemGUIImpl();
            ItemService itemService = new ItemServiceImpl();

            String[] strings1 = inventory.getItem(49).getItemMeta().getDisplayName().split("/");
            String[] strings2 = strings1[0].split(" ");
            int page = Integer.parseInt(strings2[1].replaceAll(" ", ""));

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

                    Item item;
                    item = ItemRepository.itemDataHashMap.get(itemID);

                    itemGUI.openItemSettingGUI(player, itemID);
                } else if (event.getClick().isShiftClick() && event.getClick().isRightClick()) {
                    ItemStack itemStack = inventory.getItem(slot);
                    List<String> loreList = itemStack.getItemMeta().getLore();
                    for (String lore : loreList) {
                        if(lore.contains("식별 코드:")) {
                            String[] strings = lore.split(":");
                            Integer itemID = Integer.parseInt(strings[1].replaceAll(" ", ""));
                            itemService.removeItem(itemID);
                        }
                    }

                    player.sendMessage(Reference.prefix_normal + inventory.getItem(slot).getItemMeta().getDisplayName() + " 아이템이 제거 되었습니다.");
                    itemGUI.openItemListGUI(player, page);

                }


            }


            else if(48 <= slot && slot <= 50) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.getDisplayName().contains("To")) {
                    String[] strings = itemMeta.getDisplayName().split(" ");
                    itemGUI.openItemListGUI(player, Integer.parseInt(strings[1]));
                }
            }

            // Player Inventory
            else if(54 <= slot && slot <= 89) {
                itemService.createItem(new Item(event.getCurrentItem(), event.getCurrentItem().getItemMeta().getDisplayName(), new ArrayList<>(), 0, 0, 0, 0, true, new Date().getTime(), true, true));
                player.sendMessage(Reference.prefix_normal + event.getCurrentItem().getItemMeta().getDisplayName() + " 아이템이 추가 되었습니다.");
                int maxPage = ItemRepository.itemIdSet.size() / 45;
                maxPage += ItemRepository.itemIdSet.size() % 45 == 0 ? 0 : 1;
                itemGUI.openItemListGUI(player, maxPage);

                player.sendMessage(event.getCurrentItem().toString());
            }


        }
    }
    @EventHandler
    public static void onClickItemSettingGUI(InventoryClickEvent event) {
        if (event.getView().getTitle().contains(Reference.prefix_normal + "Item Setting GUI")
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType() != Material.AIR) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            ItemGUI itemGUI = new ItemGUIImpl();

            int slot = event.getRawSlot();

            String[] strings = event.getView().getTitle().split("ID:");
            Integer itemID = Integer.parseInt(strings[1].replaceAll(" ", ""));
            Item item = ItemRepository.itemDataHashMap.get(itemID);

            ShopPluginRepository.playerCurrentInventoryTitleHashMap.put(player.getUniqueId(), event.getView().getTitle());

            switch (slot) {
                // ItemStack
                case 4:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ItemInputMode.ItemStack);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "아이템을 던져주세요. ( 취소: -c )");
                    break;

                // Name
                case 10:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ItemInputMode.ItemName);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "이름을 입력 해주세요. ( 취소: -c )");
                    break;

                // Lore
                case 11:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ItemInputMode.ItemLore);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "책과 깃펜의 한 장당 한 줄의 설명을 적은 후, 던져주세요. ( 취소: -c )");
                    player.getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
                    break;


                // Price
                case 12:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ItemInputMode.ItemPrice);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "가격을 입력 해주세요. ( 취소: -c )");
                    break;


                // Discount Price
                case 13:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ItemInputMode.ItemDiscountPrice);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "할인 가격을 입력 해주세요. ( 취소: -c )");
                    break;


                // Remain Amount
                case 14:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ItemInputMode.ItemRemainAmount);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "잔여 수량을 입력 해주세요. ( 취소: -c )");
                    break;


                // Limit Amount
                case 15:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ItemInputMode.ItemLimitAmount);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "제한 수량을 입력 해주세요. ( 취소: -c )");
                    break;


                // Is Limit Amount
                case 16:
                    if(inventory.getItem(16).getType() == Material.GREEN_CONCRETE) {
                        item.setIsLimitAmount(false);
                        inventory.setItem(16, GUI.getCustomItemStack(Material.RED_CONCRETE, ChatColor.RED + "Limit Amount of Items", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsLimitAmount()))));
                    } else {
                        item.setIsLimitAmount(true);
                        inventory.setItem(16, GUI.getCustomItemStack(Material.GREEN_CONCRETE, ChatColor.GREEN + "Limit Amount of Items", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsLimitAmount()))));

                    }
                    break;

                // Limit Date
                case 20:
                    ShopPluginRepository.playerInputModeHashMap.put(player.getUniqueId(), ItemInputMode.ItemLimitDate);
                    player.closeInventory();
                    player.sendMessage(Reference.prefix_normal + "제한 날짜를 yyyy-MM-dd HH:mm 형식으로 입력 해주세요. ( 취소: -c )");
                    break;


                // Is Limit Date
                case 21:
                    if(inventory.getItem(21).getType() == Material.GREEN_CONCRETE) {
                        item.setIsLimitDate(false);
                        inventory.setItem(21, GUI.getCustomItemStack(Material.RED_CONCRETE, ChatColor.RED + "Limit Date of Items", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsLimitDate()))));

                    } else {
                        item.setIsLimitDate(true);
                        inventory.setItem(21, GUI.getCustomItemStack(Material.GREEN_CONCRETE, ChatColor.GREEN + "Limit Date of Items", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsLimitDate()))));

                    }
                    break;

                // Is Use Item Lore
                case 23:
                    if(inventory.getItem(23).getType() == Material.GREEN_CONCRETE) {
                        item.setIsUseItemLore(false);
                        inventory.setItem(23, GUI.getCustomItemStack(Material.RED_CONCRETE, ChatColor.RED + "Use Item's Lore", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsUseItemLore()))));
                    } else {
                        item.setIsUseItemLore(true);
                        inventory.setItem(23, GUI.getCustomItemStack(Material.GREEN_CONCRETE, ChatColor.GREEN + "Use Item's Lore", Collections.singletonList(ChatColor.WHITE + String.valueOf(item.getIsUseItemLore()))));
                    }
                    break;

                case 26:
                    itemGUI.openItemListGUI(player, 1);
                    break;

                default:

            }

            inventory.setItem(4, itemGUI.getItemStackShopVer(itemID));

        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(ShopPluginRepository.playerInputModeHashMap.getOrDefault(event.getPlayer().getUniqueId(), null) instanceof ItemInputMode) {
            event.setCancelled(true);

            Player player = event.getPlayer();

            ItemInputMode mode = (ItemInputMode) ShopPluginRepository.playerInputModeHashMap.get(player.getUniqueId());


            String[] strings = ShopPluginRepository.playerCurrentInventoryTitleHashMap.get(player.getUniqueId()).split("ID:");
            Integer itemID = Integer.parseInt(strings[1].replaceAll(" ", ""));
            Item item = ItemRepository.itemDataHashMap.get(itemID);

            ItemGUI itemGUI = new ItemGUIImpl();

            if(event.getMessage().equalsIgnoreCase("-c")) {
                ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                ShopPluginRepository.playerCurrentInventoryTitleHashMap.remove(player.getUniqueId());

                Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                    itemGUI.openItemSettingGUI(player, itemID);
                });

                return;
            }

            else if(StringUtils.isNumeric(event.getMessage()) && mode != ItemInputMode.ItemName) {
                int value = Integer.parseInt(event.getMessage());

                switch(mode) {
                    case ItemPrice:
                        item.setPrice(value);
                        player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템의 가격이 " + item.getPrice() + "로 변경 되었습니다.");
                        break;

                    case ItemDiscountPrice:
                        item.setDiscountPrice(value);
                        player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템의 할인 가격이 " + item.getDiscountPrice() + "로 변경 되었습니다.");
                        break;

                    case ItemRemainAmount:
                        item.setRemainAmount(value);
                        player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템의 잔여 수량이 " + item.getRemainAmount() + "로 변경 되었습니다.");
                        break;

                    case ItemLimitAmount:
                        item.setLimitAmount(value);
                        player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템의 제한 수량이 " + item.getLimitAmount() + "로 변경 되었습니다.");
                        break;

                    default:
                        player.sendMessage(Reference.prefix_normal + "잘못된 입력입니다.");
                        break;


                }

                if(mode == ItemInputMode.ItemPrice || mode == ItemInputMode.ItemDiscountPrice || mode == ItemInputMode.ItemRemainAmount || mode == ItemInputMode.ItemLimitAmount) {
                    ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                    ShopPluginRepository.playerCurrentInventoryTitleHashMap.remove(player.getUniqueId());


                    Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                        itemGUI.openItemSettingGUI(player, itemID);
                    });
                }

            }
            else {
                String message = event.getMessage();

                switch(mode) {
                    case ItemName:
                        String itemName = ChatColorUtil.translateRGBColors(message);
                        player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템의 이름이 " + itemName + "로 변경 되었습니다.");

                        item.setName(itemName);

                        break;

                    case ItemLimitDate:
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        dateFormat.setLenient(false);
                        try {
                            Date date = dateFormat.parse(message);

                             item.setLimitDate(date.getTime());
                             player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템의 제한 날짜가 " + dateFormat.format(new Date(item.getLimitDate())) + "로 변경 되었습니다.");

                            ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                            ShopPluginRepository.playerCurrentInventoryTitleHashMap.remove(player.getUniqueId());


                            Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                                itemGUI.openItemSettingGUI(player, itemID);
                            });


                        } catch (ParseException e) {
                            player.sendMessage(Reference.prefix_normal + "잘못된 입력입니다.");
                        }

                        break;

                    default:
                        player.sendMessage(Reference.prefix_normal + "잘못된 입력입니다.");
                        break;
                }

                if(mode == ItemInputMode.ItemName) {
                    ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                    ShopPluginRepository.playerCurrentInventoryTitleHashMap.remove(player.getUniqueId());


                    Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                        itemGUI.openItemSettingGUI(player, itemID);
                    });
                }


            }



        }
    }

    @EventHandler
    public void onDropPlayerItem(PlayerDropItemEvent event) {
        if(ShopPluginRepository.playerInputModeHashMap.getOrDefault(event.getPlayer().getUniqueId(), null) instanceof ItemInputMode) {
            ItemInputMode mode = (ItemInputMode) ShopPluginRepository.playerInputModeHashMap.get(event.getPlayer().getUniqueId());

            if (mode == ItemInputMode.ItemLore || mode == ItemInputMode.ItemStack) {
                event.setCancelled(true);
                Player player = event.getPlayer();
                ItemGUI itemGUI = new ItemGUIImpl();

                String[] strings = ShopPluginRepository.playerCurrentInventoryTitleHashMap.get(player.getUniqueId()).split("ID:");
                Integer itemID = Integer.parseInt(strings[1].replaceAll(" ", ""));
                Item item = ItemRepository.itemDataHashMap.get(itemID);

                if (mode == ItemInputMode.ItemLore && event.getItemDrop().getItemStack().getType() == Material.WRITABLE_BOOK) {
                    ItemStack itemStack = event.getItemDrop().getItemStack();
                    BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();


                    List<String> lore = new ArrayList<>();
                    for (String string : bookMeta.getPages()) {
                        lore.add(ChatColorUtil.translateRGBColors(string));
                    }

                    item.setLore(lore);

                    player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템의 설명이 아래와 같이 변경 되었습니다.");
                    for (String s : item.getLore()) {
                        player.sendMessage(s);
                    }

                    ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                    ShopPluginRepository.playerCurrentInventoryTitleHashMap.remove(player.getUniqueId());

                    Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                        itemGUI.openItemSettingGUI(player, itemID);
                    });

                }
                else if (mode == ItemInputMode.ItemStack) {
                    player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템이 " + event.getItemDrop().getItemStack().getItemMeta().getDisplayName() + "로 변경 되었습니다.");
                    item.setItemStack(event.getItemDrop().getItemStack());
                    item.setName(event.getItemDrop().getItemStack().getItemMeta().getDisplayName());


                    ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                    ShopPluginRepository.playerCurrentInventoryTitleHashMap.remove(player.getUniqueId());

                    Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                        itemGUI.openItemSettingGUI(player, itemID);
                    });
                }
                else {
                    player.sendMessage(Reference.prefix_normal + "잘못된 입력입니다.");
                }
            }
        }
    }
}
