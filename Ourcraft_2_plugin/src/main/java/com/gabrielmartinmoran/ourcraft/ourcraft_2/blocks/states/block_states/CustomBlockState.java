package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.deserializers.InventoryItemsDeserializer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.serializers.InventoryItemsSerializer;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import jline.internal.Nullable;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class CustomBlockState {
    protected String ownerPlayerName;
    protected Location location;
    protected World world;
    @Nullable
    protected Inventory inventory;
    protected static Gson gson = new Gson();

    public CustomBlockState(String ownerPlayerName, World world, Location location) {
        this.ownerPlayerName = ownerPlayerName;
        this.world = world;
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    public World getWorld() {
        return this.world;
    }

    @Nullable
    public Inventory getInventory() {
        return this.inventory;
    }

    protected void updateLight() {
        Block block = this.world.getBlockAt(this.location);
        Collection<Entity> entities = this.getWorld().getNearbyEntities(this.getItemDisplayLocation(), 0.5, 0.5, 0.5,
                e -> (e instanceof ItemDisplay));
        for (Entity entity : entities) {
            byte[] lightLevels = new byte[]{
                    block.getRelative(BlockFace.UP).getLightLevel(),
                    block.getRelative(BlockFace.DOWN).getLightLevel(),
                    block.getRelative(BlockFace.EAST).getLightLevel(),
                    block.getRelative(BlockFace.WEST).getLightLevel(),
                    block.getRelative(BlockFace.NORTH).getLightLevel(),
                    block.getRelative(BlockFace.SOUTH).getLightLevel()
            };
            byte[] lightFromSky = new byte[]{
                    block.getRelative(BlockFace.UP).getLightFromSky(),
                    block.getRelative(BlockFace.DOWN).getLightFromSky(),
                    block.getRelative(BlockFace.EAST).getLightFromSky(),
                    block.getRelative(BlockFace.WEST).getLightFromSky(),
                    block.getRelative(BlockFace.NORTH).getLightFromSky(),
                    block.getRelative(BlockFace.SOUTH).getLightFromSky()
            };
            ((ItemDisplay) entity).setBrightness(new Display.Brightness(
                    Collections.max((Collection<? extends Byte>) Arrays.asList(ArrayUtils.toObject(lightLevels))),
                    Collections.max((Collection<? extends Byte>) Arrays.asList(ArrayUtils.toObject(lightFromSky)))
            ));
        }
    }

    protected Location getItemDisplayLocation() {
        Location itemDisplayLocation = this.getLocation().clone();
        itemDisplayLocation.setX(itemDisplayLocation.getX() + 0.5);
        itemDisplayLocation.setY(itemDisplayLocation.getY() + 0.5);
        itemDisplayLocation.setZ(itemDisplayLocation.getZ() + 0.5);
        return itemDisplayLocation;
    }

    protected void removeDisplayEntity() {
        Collection<Entity> entities = this.getWorld().getNearbyEntities(this.getItemDisplayLocation(), 0.5, 0.5, 0.5,
                e -> (e instanceof ItemDisplay));
        for (Entity entity : entities) {
            entity.remove();
        }
    }

    public boolean isLoaded() {
        return this.getWorld().isChunkLoaded(this.getLocation().getChunk());
    }

    /**
     * Not only summons the ItemDisplay in the world but also returns it in order to prevent searching it in case of
     * using it further
     *
     * @return
     */
    protected ItemDisplay createItemDisplay() {
        ItemDisplay entity = (ItemDisplay) this.getWorld().spawnEntity(this.getItemDisplayLocation(),
                EntityType.ITEM_DISPLAY);
        entity.setItemStack(this.getBlockItem());
        entity.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.HEAD);
        // Adjust the direction if the real block allows it
        BlockData blockData = this.getWorld().getBlockAt(this.getLocation()).getBlockData();
        if (blockData instanceof Directional) {
            switch (((Directional) blockData).getFacing()) {
                case NORTH:
                    entity.setRotation(0, 0);
                    break;
                case EAST:
                    entity.setRotation(90, 0);
                    break;
                case SOUTH:
                    entity.setRotation(180, 0);
                    break;
                case WEST:
                    entity.setRotation(270, 0);
                    break;
                default:
                    break;
            }
        }
        return entity;
    }

    public CustomBlockStateSerialized serialize() {
        CustomBlockStateSerialized serialized = new CustomBlockStateSerialized(this.getClass().getSimpleName());
        serialized.put("ownerPlayerName", this.getOwnerPlayerName());
        serialized.put("worldName", this.getWorld().getName());
        serialized.put("location", this.gson.toJson(this.getLocation().serialize()));
        if (this.getInventory() != null) {
            Inventory inventoryClone = Bukkit.createInventory(null, this.getInventory().getContents().length, "");
            ArrayList<Integer> indexesToAvoidSerialization = this.getIndexesToAvoidInventorySerialization();
            for (int i = 0; i < this.getInventory().getContents().length; i++) {
                if (!indexesToAvoidSerialization.contains(i)) {
                    inventoryClone.setItem(i, this.getInventory().getItem(i));
                }
            }
            serialized.put("inventoryItems", InventoryItemsSerializer.serialize(inventoryClone));
        } else {
            serialized.put("inventoryItems", "null");
        }

        return serialized;
    }

    public String getOwnerPlayerName() {
        return this.ownerPlayerName;
    }

    public static CustomBlockState deserialize(CustomBlockStateSerialized serialized) {
        throw new NotImplementedException();
    }

    protected static String deserializeOwnerPlayerName(CustomBlockStateSerialized serialized) {
        return serialized.get("ownerPlayerName");
    }

    protected static World deserializeWorld(CustomBlockStateSerialized serialized) {
        return Bukkit.getWorld(serialized.get("worldName"));
    }

    protected static Location deserializeLocation(CustomBlockStateSerialized serialized) {
        return Location.deserialize(gson.fromJson(serialized.get("location"), new TypeToken<Map<String, Object>>() {
        }.getType()));
    }

    protected void populateInventoryFromSerialized(CustomBlockStateSerialized serialized) {
        // The sizes should be the same
        String inventoryItems = serialized.get("inventoryItems");
        if (inventoryItems.equals("null")) return;
        ItemStack[] deserializedInventoryItems = InventoryItemsDeserializer.deserialize(serialized.get(
                "inventoryItems"));
        for (int i = 0; i < this.getInventory().getContents().length; i++) {
            if (deserializedInventoryItems[i] != null) {
                this.getInventory().setItem(i, deserializedInventoryItems[i]);
            }
        }
    }


    public abstract void onTick();

    public void onDestroy(Player player) {
        this.removeDisplayEntity();
        this.dropInventoryItems();
    }

    protected abstract void dropInventoryItems();

    public abstract void onInteract(Player player);

    public void onInventoryItemClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null && item.getType().equals(Material.BARRIER)) {
            event.setCancelled(true);
        }
    }

    public abstract ItemStack getBlockItem();

    public void onPlace() {
        this.createItemDisplay();
        this.updateLight();
    }

    public abstract boolean canReceiveItemsFromHopper();

    /**
     * This method is used for getting items from other inventories. For instance hoppers or minecarts with hopper.
     *
     * @param otherInventory
     */
    public abstract void tryGetFromExternalInventory(Inventory otherInventory);

    protected void tryDropFromInventory(int inventoryIndex) {
        if (this.getInventory() == null) return;
        ItemStack item = this.getInventory().getItem(inventoryIndex);
        if (item != null) this.getWorld().dropItem(this.getLocation(), item);
    }

    protected abstract ArrayList<Integer> getIndexesToAvoidInventorySerialization();
}
