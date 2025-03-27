package com.popogonry.shopPlugin.item;

import com.popogonry.shopPlugin.Reference;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemGUIEvent implements Listener {

    @EventHandler
    public static void onClickItemListGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(Reference.prefix_normal + "Item List GUI")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            int slot = event.getRawSlot();

            player.sendMessage("slot = " + slot);

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

                itemGUI.openItemSettingGUI(player, item);
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

}
