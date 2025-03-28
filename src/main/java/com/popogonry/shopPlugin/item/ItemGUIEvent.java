package com.popogonry.shopPlugin.item;

import com.popogonry.shopPlugin.ChatInputMode;
import com.popogonry.shopPlugin.Reference;
import com.popogonry.shopPlugin.ShopPluginRepository;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.print.Book;

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

            // Item List
            if(0 <= slot && slot <= 45) {
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


            switch (slot) {
                // ItemStack
                case 4:


                // Name
                case 10:
                    ShopPluginRepository.playerChatInputModeHashMap.put(player.getUniqueId(), ChatInputMode.ItemName);
                    ShopPluginRepository.playerCurrentInventoryHashMap.put(player.getUniqueId(), player.getInventory());
                    player.closeInventory();
                    break;

                // Lore
                case 11:
                    ShopPluginRepository.playerChatInputModeHashMap.put(player.getUniqueId(), ChatInputMode.ItemLore);
                    ShopPluginRepository.playerCurrentInventoryHashMap.put(player.getUniqueId(), player.getInventory());
                    player.closeInventory();
                    break;

                // Price
                case 12:
                    ShopPluginRepository.playerChatInputModeHashMap.put(player.getUniqueId(), ChatInputMode.ItemPrice);
                    ShopPluginRepository.playerCurrentInventoryHashMap.put(player.getUniqueId(), player.getInventory());
                    player.closeInventory();
                    break;

                // Discount Price
                case 13:
                    ShopPluginRepository.playerChatInputModeHashMap.put(player.getUniqueId(), ChatInputMode.ItemDiscountPrice);
                    ShopPluginRepository.playerCurrentInventoryHashMap.put(player.getUniqueId(), player.getInventory());
                    player.closeInventory();
                    break;

                // Remain Amount
                case 14:
                    ShopPluginRepository.playerChatInputModeHashMap.put(player.getUniqueId(), ChatInputMode.ItemRemainAmount);
                    ShopPluginRepository.playerCurrentInventoryHashMap.put(player.getUniqueId(), player.getInventory());
                    player.closeInventory();
                    break;

                // Limit Amount
                case 15:
                    ShopPluginRepository.playerChatInputModeHashMap.put(player.getUniqueId(), ChatInputMode.ItemLimitAmount);
                    ShopPluginRepository.playerCurrentInventoryHashMap.put(player.getUniqueId(), player.getInventory());
                    player.closeInventory();


                // Is Limit Amount
                case 16:
                    if(inventory.getItem(16).getType() == Material.GREEN_CONCRETE) {
                        item.setIsLimitAmount(false);
                    } else {
                        item.setIsLimitAmount(true);
                    }
                    itemGUI.openItemSettingGUI(player, itemID);

                    break;

                // Limit Date
                case 20:
                    ShopPluginRepository.playerChatInputModeHashMap.put(player.getUniqueId(), ChatInputMode.ItemLimitDate);
                    ShopPluginRepository.playerCurrentInventoryHashMap.put(player.getUniqueId(), player.getInventory());
                    player.closeInventory();
                    break;

                // Is Limit Date
                case 21:
                    if(inventory.getItem(21).getType() == Material.GREEN_CONCRETE) {
                        item.setIsLimitDate(false);
                    } else {
                        item.setIsLimitDate(true);
                    }
                    itemGUI.openItemSettingGUI(player, itemID);

                    break;

                // Is Use Item Lore
                case 23:
                    if(inventory.getItem(23).getType() == Material.GREEN_CONCRETE) {
                        item.setIsUseItemLore(false);
                    } else {
                        item.setIsUseItemLore(true);
                    }
                    itemGUI.openItemSettingGUI(player, itemID);

                    break;

                default:


            }



        }
    }

    @EventHandler
    public static void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.sendMessage(String.valueOf(event.getRawSlot()));
    }
}
