package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.helpers.ManaMachineEngine;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.ManaExporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.ManaPool;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.ManaGenerator;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.ProgressBar;
import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ManaGeneratorBlockState extends CustomBlockState implements ManaProvider {
    private boolean isSmelting;
    private int completedPercentage;
    private ManaPool manaPool;
    private ManaMachineEngine machineEngine;
    private ManaTank tank;
    private final int INVENTORY_SIZE = 36;
    private final int FUEL_INVENTORY_INDEX = 30;
    private final int PROGRESS_BAR_INVENTORY_INDEX = 31;
    private final int MAX_MANA_CAPACITY = 500;
    private final int MANA_GENERATED_PER_TICK = 1;
    private final ItemStack MANA_ESSENCE_ITEM = new ManaEssence().getItem();

    public ManaGeneratorBlockState(String ownerPlayerName, World world, Location location) {
        super(ownerPlayerName, world, location);
        this.tank = new ManaTank(this.MAX_MANA_CAPACITY, 0);
        this.manaPool = new ManaPool(this.getName(), location, this.tank);
        this.machineEngine = new ManaMachineEngine(this);
        this.isSmelting = false;
        this.completedPercentage = 0;
        this.createInventory();
    }

    public boolean getIsSmelting() {
        return this.isSmelting;
    }

    public int getCompletedPercentage() {
        return this.completedPercentage;
    }

    public void setIsSmelting(boolean isSmelting) {
        this.isSmelting = isSmelting;
    }

    public void setCompletedPercentage(int completedPercentage) {
        this.completedPercentage = completedPercentage;
        if (this.completedPercentage > 100) this.completedPercentage = 100;
        if (this.completedPercentage < 0) this.completedPercentage = 0;
    }

    public CustomBlockStateSerialized serialize() {
        CustomBlockStateSerialized serialized = super.serialize();
        serialized.put("isSmelting", this.gson.toJson(this.getIsSmelting()));
        serialized.put("completedPercentage", this.gson.toJson(this.getCompletedPercentage()));
        serialized.put("currentMana", this.gson.toJson(this.tank.getCurrentMana()));
        return serialized;
    }

    public static ManaGeneratorBlockState deserialize(CustomBlockStateSerialized serialized) {
        ManaGeneratorBlockState state = new ManaGeneratorBlockState(
                deserializeOwnerPlayerName(serialized),
                deserializeWorld(serialized),
                deserializeLocation(serialized)
        );
        state.populateInventoryFromSerialized(serialized);
        state.setIsSmelting(gson.fromJson(serialized.get("isSmelting"), Boolean.class));
        state.setCompletedPercentage(gson.fromJson(serialized.get("completedPercentage"), Integer.class));
        int currentMana = gson.fromJson(serialized.get("currentMana"), Integer.class);
        state.tank = new ManaTank(state.MAX_MANA_CAPACITY, currentMana);
        state.manaPool = new ManaPool(state.getName(), state.location, state.tank);
        return state;
    }

    @Override
    public void onTick() {
        this.updateLight();
        this.machineEngine.onTick(false);
        if (this.getIsSmelting()) {
            this.manaPool.store(this.MANA_GENERATED_PER_TICK);
            this.setCompletedPercentage(this.getCompletedPercentage() + 1);
            this.getWorld().spawnParticle(Particle.GLOW, this.getItemDisplayLocation(), 5);
            this.getWorld().playSound(this.getItemDisplayLocation(), Sound.BLOCK_BLASTFURNACE_FIRE_CRACKLE, 1f, 1f);
            if (this.getCompletedPercentage() == 100) {
                this.setCompletedPercentage(0);
                this.setIsSmelting(false);
            }
        } else if (this.hasValidFuel() && this.manaPool.hasSpace()) {
            ItemStack fuel = this.getFuel();
            if (fuel.getAmount() > 0) {
                fuel.setAmount(fuel.getAmount() - 1);
            } else {
                fuel = null;
            }
            this.setIsSmelting(true);
            this.setFuel(fuel);
        }
        this.updateProgressBar();
        this.machineEngine.updateTank();
    }

    @Override
    protected void dropInventoryItems() {
        this.tryDropFromInventory(this.FUEL_INVENTORY_INDEX);
        for (int index : this.machineEngine.getDropIndexes()) {
            this.tryDropFromInventory(index);
        }
    }

    @Override
    public void onInteract(Player player) {
        player.openInventory(this.getInventory());
    }

    @Override
    public ItemStack getBlockItem() {
        return new ManaGenerator().getItem();
    }

    @Override
    public boolean canReceiveItemsFromHopper() {
        return true;
    }

    @Override
    public void tryGetFromExternalInventory(Inventory otherInventory) {
        ItemStack fuel = this.getFuel();
        if (fuel != null && fuel.getAmount() == fuel.getMaxStackSize()) return;
        for (int i = 0; i < otherInventory.getContents().length; i++) {
            ItemStack hopperItem = otherInventory.getItem(i);
            if (hopperItem == null || hopperItem.getAmount() == 0) continue;
            boolean itemTransferred = false;
            if (fuel == null) {
                fuel = hopperItem.clone();
                fuel.setAmount(1);
                this.setFuel(fuel);
                itemTransferred = true;
            } else if (fuel.isSimilar(hopperItem)) {
                fuel.setAmount(fuel.getAmount() + 1);
                this.setFuel(fuel);
                itemTransferred = true;
            }
            if (itemTransferred) {
                int hopperItemAmount = hopperItem.getAmount() - 1;
                if (hopperItemAmount == 0) {
                    otherInventory.setItem(i, null);
                } else {
                    hopperItem.setAmount(hopperItemAmount);
                    otherInventory.setItem(i, hopperItem);
                }
                break;
            }
        }
    }

    private void createInventory() {
        this.machineEngine.createInventory(this.INVENTORY_SIZE, new int[]{
                this.FUEL_INVENTORY_INDEX,
                this.PROGRESS_BAR_INVENTORY_INDEX
        });
        this.updateProgressBar();
    }

    private void updateProgressBar() {
        this.getInventory().setItem(this.PROGRESS_BAR_INVENTORY_INDEX,
                ProgressBar.generateAtPercentage(this.getCompletedPercentage()));
    }

    @Nullable
    public ItemStack getFuel() {
        return this.inventory.getItem(this.FUEL_INVENTORY_INDEX);
    }

    public void setFuel(@Nullable ItemStack item) {
        this.inventory.setItem(this.FUEL_INVENTORY_INDEX, item);
    }

    public boolean hasValidFuel() {
        ItemStack fuel = this.getFuel();
        return fuel != null && fuel.isSimilar(this.MANA_ESSENCE_ITEM);
    }

    @Override
    public ManaTank getTank() {
        return this.manaPool.getTank();
    }

    public String getName() {
        return "Generador de maná";
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public ItemStack[] getValidBuffs() {
        return new ItemStack[0];
    }

    @Override
    public ManaExporter getManaExporter() {
        return this.manaPool;
    }

    @Override
    protected ArrayList<Integer> getIndexesToAvoidInventorySerialization() {
        return this.machineEngine.getIndexesToAvoidInventorySerialization();
    }
}
