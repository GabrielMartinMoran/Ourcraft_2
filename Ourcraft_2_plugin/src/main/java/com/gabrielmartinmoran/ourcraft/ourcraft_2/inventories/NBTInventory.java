package com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.serializers.SerializedItem;
import com.google.gson.Gson;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class NBTInventory {
    protected ItemStack item;
    protected NBTItem nbt;
    protected int size;
    protected int totalSize;
    protected String name;

    protected Block block;

    private Inventory inventory;

    private final int SIZE_PER_ROW = 9;
    public static final String INVENTORY_JSON_KEY = "inventoryJson";
    public static final String INVENTORY_SIZE_KEY = "inventorySize";
    public static final String HAS_INVENTORY_KEY = "hasInventory";

    public static void prepareItem(ItemStack item, int size) {
        NBTItem nbt = new NBTItem(item, true);
        nbt.setBoolean(HAS_INVENTORY_KEY, true);
        nbt.setInteger(INVENTORY_SIZE_KEY, size);
    }

    public NBTInventory(ItemStack item) {
        this(item, item.getItemMeta().getDisplayName());
    }


    public NBTInventory(ItemStack item, String name) {
        this.item = item;
        this.nbt = new NBTItem(item, true);

        if (!(this.nbt.hasTag(this.HAS_INVENTORY_KEY) && this.nbt.getBoolean(this.HAS_INVENTORY_KEY))) {
            throw new AssertionError(String.format("Item %s does not have an inventory", item));
        }

        this.size = this.nbt.getInteger(this.INVENTORY_SIZE_KEY);
        // Complete with slots until the size is a multiple of SIZE_PER_ROW
        this.totalSize = size;
        if (this.totalSize % this.SIZE_PER_ROW != 0)
            this.totalSize += this.SIZE_PER_ROW - (this.totalSize % this.SIZE_PER_ROW);

        this.name = name;

        this.inventory = Bukkit.createInventory(null, this.totalSize, this.name);
        if (nbt.hasKey(this.INVENTORY_JSON_KEY)) {
            Gson gson = new Gson();
            ArrayList<String> serialized = gson.fromJson(nbt.getString(this.INVENTORY_JSON_KEY), ArrayList.class);
            for (int i = 0; i < serialized.size(); i++) {
                if (serialized.get(i) != null) {
                    ItemStack itemStack = SerializedItem.fromJson(serialized.get(i));
                    this.inventory.setItem(i, itemStack);
                }
            }
        } else {
            ItemStack lockedSlot = this.getLockedSlotItem();
            int lockedSpaces = this.totalSize - this.size;
            int leftLockedSpaces = lockedSpaces / 2;
            int rightLockedSpaces = lockedSpaces / 2;
            // If it's odd
            if (lockedSpaces % 2 != 0) rightLockedSpaces++;
            for (int i = 0; i < leftLockedSpaces; i++) {
                this.inventory.setItem(i, lockedSlot);
            }
            for (int i = 0; i < rightLockedSpaces; i++) {
                this.inventory.setItem(leftLockedSpaces + this.size + i, lockedSlot);
            }
        }
    }

    public void save() {
        Gson gson = new Gson();
        ArrayList<String> serializedInv = new ArrayList<String>();
        for (int i = 0; i < this.inventory.getStorageContents().length; i++) {
            ItemStack item = this.inventory.getStorageContents()[i];
            if (item != null) {
                serializedInv.add(SerializedItem.serialize(this.inventory.getItem(i)));
            } else {
                serializedInv.add(null);
            }
        }
        String json = gson.toJson(serializedInv);
        this.nbt.setString(this.INVENTORY_JSON_KEY, json);
    }

    public ItemStack getLockedSlotItem() {
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName(" ");
        barrierMeta.setCustomModelData(CustomItemsModelData.LOCKED_INVENTORY_SLOT.getModelData());
        barrier.setItemMeta(barrierMeta);
        return barrier;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }
}
