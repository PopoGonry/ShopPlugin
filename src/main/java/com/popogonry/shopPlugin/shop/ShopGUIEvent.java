package com.popogonry.shopPlugin.shop;

import com.popogonry.shopPlugin.ChatColorUtil;
import com.popogonry.shopPlugin.Reference;
import com.popogonry.shopPlugin.ShopPlugin;
import com.popogonry.shopPlugin.ShopPluginRepository;
import com.popogonry.shopPlugin.cash.CashService;
import com.popogonry.shopPlugin.cash.CashServiceImpl;
import com.popogonry.shopPlugin.item.*;
import it.unimi.dsi.fastutil.Hash;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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
                else if(event.getClick().isRightClick()) {
                    shopGUI.openShopGUI(player, itemMeta.getDisplayName(), 1);
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
    public static void onClickShopGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(ShopPluginRepository.pluginConfig.getCashShopGUIDisplayName())
                && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            int slot = event.getRawSlot();

            ShopGUI shopGUI = new ShopGUIImpl();
            ShopService shopService = new ShopServiceImpl();

            String displayName = inventory.getItem(49).getItemMeta().getDisplayName();

            Shop shop = ShopRepository.shopDataHashMap.get(displayName.substring(displayName.indexOf(" ") + 1));

            // Item List
            // 좌 클릭 아이템 배치, 아이템 리스트로 / 우클릭 아이템 세팅으로

            String[] strings1 = inventory.getItem(49).getItemMeta().getLore().get(0).split("/");
            String[] strings2 = strings1[0].split(" ");
            int page = Integer.parseInt(strings2[2].replaceAll(" ", ""));

            if(0 <= slot && slot <= 44) {
                if(event.getClick().isLeftClick()) {
                    Item item = ItemRepository.itemDataHashMap.get(shop.getItemHashMap().get(slot + ((page - 1) * 45)));

                    // 갯수 제한을 하는데, 남은 갯수가 0개 이하일경우,
                    if(item.getIsLimitAmount() && item.getRemainAmount() <= 0) {
                        player.sendMessage(Reference.prefix_error + "남은 수량이 없습니다.");
                        return;
                    }

                    // 날짜 제한을 하는데, 제한 날짜 현재 날짜가 뒤인경우
                    if(item.getIsLimitDate()) {
                        Date limitDate = new Date(item.getLimitDate());
                        if(new Date().compareTo(limitDate) > 0) {
                            player.sendMessage(Reference.prefix_error + "판매 기간이 지났습니다.");
                            return;
                        }
                    }

                    // 캐시가 부족할 때,
                    CashService cashService = new CashServiceImpl();
                    if(cashService.getCash(player.getUniqueId()) < item.getDiscountPrice()) {
                        player.sendMessage(Reference.prefix_error + "캐시가 부족합니다.");
                        return;
                    }

                    // 인벤토리 공간 확인
                    boolean hasEmptySlot = false;
                    for(int playerInventorySlot = 0; playerInventorySlot < 36; playerInventorySlot++) {
                        ItemStack itemStack = player.getInventory().getItem(playerInventorySlot);
                        if(itemStack == null || itemStack.getType() == Material.AIR) hasEmptySlot = true;
                    }

                    if(!hasEmptySlot) {
                        player.sendMessage(Reference.prefix_error + "인벤토리에 빈 공간이 없습니다.");
                        return;
                    }

                    // 아이템 지급
                    player.getInventory().addItem(item.getItemStack());
                    // 캐시 차감
                    cashService.setCash(player.getUniqueId(), cashService.getCash(player.getUniqueId()) - item.getDiscountPrice());

                    // 남은 갯수 차감
                    if(item.getIsLimitAmount()) {
                        item.setRemainAmount(item.getRemainAmount()-1);
                    }

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player.sendMessage(Reference.prefix_normal + item.getName() + " 아이템을 구매 하였습니다.");
                    player.sendMessage(Reference.prefix_normal + player.getName() + "의 캐시 : " + cashService.getCash(player.getUniqueId()));

                    shopGUI.openShopGUI(player, shop.getName(), page);
                }
            }
            else if(48 <= slot && slot <= 50) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.getDisplayName().contains("To")) {
                    String[] strings = itemMeta.getDisplayName().split(" ");
                    shopGUI.openShopGUI(player, shop.getName(), Integer.parseInt(strings[1]));
                }
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
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
                    String shopName = event.getMessage();

                    if(shopService.createShop(new Shop(shopName, new HashMap<>(), 1))) {
                        player.sendMessage(Reference.prefix_normal + shopName + " 상점이 생성 되었습니다.");

                        ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                        ShopPluginRepository.playerCurrentInventoryHashMap.remove(player.getUniqueId());
                        Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                            shopGUI.openShopSettingGUI(player, shopName);
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
                            String shopName = message;
                            if(shopService.createShop(new Shop(shopName, shop.getItemHashMap(), shop.getSize()))) {
                                player.sendMessage(Reference.prefix_normal + shop.getName() + " 상점의 이름이 " + shopName + "로 변경 되었습니다.");

                                shopService.removeShop(shop.getName());

                                ShopPluginRepository.playerInputModeHashMap.remove(player.getUniqueId());
                                ShopPluginRepository.playerCurrentInventoryHashMap.remove(player.getUniqueId());
                                Bukkit.getScheduler().runTask(ShopPlugin.getServerInstance(), () -> {
                                    shopGUI.openShopSettingGUI(player, shopName);
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
