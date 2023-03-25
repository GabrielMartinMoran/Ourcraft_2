package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.serializers.SerializedItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.ItemUtils;
import com.google.gson.Gson;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BagListener implements Listener {

    private final int SIZE_PER_ROW = 9;
    private HashMap<Inventory, ItemStack> openInventories;
    private ItemUtils itemUtils;

    public BagListener() {
        this.openInventories = new HashMap<Inventory, ItemStack>();
        this.itemUtils = new ItemUtils();
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();
        if (isBag(item) && (action.equals(Action.RIGHT_CLICK_AIR) || (action.equals(Action.RIGHT_CLICK_BLOCK)))) {
            this.showBagInventory(event.getPlayer(), item);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (this.openInventories.containsKey(inventory)) {
            this.saveBagInventory(inventory, this.openInventories.get(inventory));
            this.damageBag((Player) event.getPlayer(), this.openInventories.get(inventory), inventory);
            this.openInventories.remove(inventory);
        }
    }

    @EventHandler
    public void onInventoryItemClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        if (this.openInventories.containsKey(inventory)) {
            if (this.isBag(item) || (
                    item != null && (
                            item.getType().equals(Material.BARRIER) ||
                                    item.getType().toString().contains("SHULKER_BOX"))
            )
            ) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isBag(ItemStack item) {
        if (item == null) return false;
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey("isBag") && nbt.getBoolean("isBag");
    }

    private Inventory getOrCreateBagInventory(ItemStack bag) {
        NBTItem nbt = new NBTItem(bag);
        int realSize = this.getBagSize(bag);
        int totalSize = this.getBagSize(bag);
        if (totalSize % SIZE_PER_ROW != 0) totalSize += SIZE_PER_ROW - (totalSize % SIZE_PER_ROW);
        Inventory inventory = Bukkit.createInventory(null, totalSize, bag.getItemMeta().getDisplayName());
        if (nbt.hasKey("inventoryJson")) {
            Gson gson = new Gson();
            //ArrayList<Map<String, Object>> serialized = gson.fromJson(nbt.getString("inventoryJson"), ArrayList.class);
            ArrayList<String> serialized = gson.fromJson(nbt.getString("inventoryJson"), ArrayList.class);
            for (int i = 0; i < serialized.size(); i++) {
                if (serialized.get(i) != null) {
                    ItemStack item = SerializedItem.fromJson(serialized.get(i));
                    inventory.setItem(i, item);
                    /*
                    ItemMeta itemMeta = (ItemMeta) ConfigurationSerialization.deserializeObject((Map<String, Object>) serialized.get(i).get("meta"));
                    ItemStack item = (ItemStack) Serializer.deserialize(serialized.get(i));
                    item.setItemMeta(itemMeta);
                    inventory.setItem(i, item);
                     */
                    //inventory.setItem(i, this.deserializeItem(deserialized.get(i)));
                    //inventory.setItem(i, ItemStack.deserialize((Map<String, Object>) deserialized.get(i)));
                }
            }
        } else {
            ItemStack barrier = new ItemStack(Material.BARRIER);
            ItemMeta barrierMeta = barrier.getItemMeta();
            barrierMeta.setDisplayName("Espacio inválido");
            barrier.setItemMeta(barrierMeta);
            for (int i = 0; i < totalSize - realSize; i++) {
                inventory.setItem(realSize + i, barrier);
            }
        }
        return inventory;
    }

    private void saveBagInventory(Inventory inventory, ItemStack bag) {
        Gson gson = new Gson();
        //ArrayList<Map<String, Object>> serializedInv = new ArrayList<Map<String, Object>>();
        ArrayList<String> serializedInv = new ArrayList<String>();
        for (int i = 0; i < inventory.getStorageContents().length; i++) {
            ItemStack item = inventory.getStorageContents()[i];
            if (item != null) {
                serializedInv.add(SerializedItem.serialize(inventory.getItem(i)));
            } else {
                serializedInv.add(null);
            }
        }
        String json = gson.toJson(serializedInv);
        NBTItem nbt = new NBTItem(bag);
        nbt.setString("inventoryJson", json);
        bag.setItemMeta(nbt.getItem().getItemMeta());
    }

    private int getBagSize(ItemStack item) {
        return (new NBTItem(item)).getInteger("bagSize");
    }

    private void showBagInventory(Player player, ItemStack item) {
        Inventory bagInventory = this.getOrCreateBagInventory(item);
        this.openInventories.put(bagInventory, item);
        player.openInventory(bagInventory);
    }

    private void damageBag(Player player, ItemStack bag, Inventory inventory) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) return;
        boolean destroyed = this.itemUtils.reduceItemDurability(player, bag);
        if (destroyed) {
            for (ItemStack item : inventory.getContents()) {
                if (item.getType() != Material.BARRIER) player.getWorld().dropItem(player.getLocation(), item);
            }
        }
    }
}
