package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.helpers.ManaMachineEngine;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input.ManaImporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaConsumer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.ManaBottler;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.ManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.ProgressBar;
import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ManaBottlerBlockState extends CustomBlockState implements ManaConsumer {
    private boolean inProgress;
    private int completedPercentage;
    private ManaImporter manaImporter;
    private ManaMachineEngine machineEngine;
    private ManaTank tank;
    private final int INVENTORY_SIZE = 36;
    private final int MANA_EXTRACTOR_RANGE = 10;
    private final int INPUT_INVENTORY_INDEX = 30;
    private final int PROGRESS_BAR_INVENTORY_INDEX = 31;
    private final int OUTPUT_INVENTORY_INDEX = 32;
    private final int MANA_CONSUMED_PER_PERCENT = 10;
    private final int MAX_MANA_CAPACITY = 200;
    private final int MANA_EXTRACTION_RATE = 50;
    private final ItemStack MANA_POTION = new ManaPotion().getItem();

    public ManaBottlerBlockState(String ownerPlayerName, World world, Location location) {
        super(ownerPlayerName, world, location);
        this.tank = new ManaTank(this.MAX_MANA_CAPACITY, 0);
        this.manaImporter = new ManaImporter(this.location, this.MANA_EXTRACTOR_RANGE, this.MANA_EXTRACTION_RATE);
        this.machineEngine = new ManaMachineEngine(this);
        this.createInventory();
    }

    @Override
    public void onTick() {
        this.updateLight();
        this.machineEngine.onTick(false);
        if (this.canGenerateOutput() && this.tank.hasEnough(this.MANA_CONSUMED_PER_PERCENT)) {
            if (this.inProgress) {
                this.tank.subtract(this.MANA_CONSUMED_PER_PERCENT);
                this.completedPercentage++;
                if (this.completedPercentage >= 100) {
                    this.completedPercentage = 0;
                    this.inProgress = false;
                    this.generateOutput();
                }
            } else if (this.hasValidInput()) {
                this.tank.subtract(this.MANA_CONSUMED_PER_PERCENT);
                this.subtractInput();
                this.inProgress = true;
            }
            this.updateProgressBar();
        }
        this.machineEngine.updateTank();
    }

    private void subtractInput() {
        ItemStack currentInput = this.inventory.getItem(this.INPUT_INVENTORY_INDEX);
        ItemStack updatedInput;
        if (currentInput.getAmount() == 1) {
            updatedInput = null;
        } else {
            currentInput.setAmount(currentInput.getAmount() - 1);
            updatedInput = currentInput;
        }
        this.inventory.setItem(this.INPUT_INVENTORY_INDEX, updatedInput);
    }

    private boolean hasValidInput() {
        ItemStack currentInput = this.inventory.getItem(this.INPUT_INVENTORY_INDEX);
        return currentInput != null && currentInput.getType() == Material.GLASS_BOTTLE;
    }

    @Nullable
    private ItemStack getCurrentOutput() {
        return this.inventory.getItem(this.OUTPUT_INVENTORY_INDEX);
    }

    private boolean canGenerateOutput() {
        ItemStack currentOutput = this.getCurrentOutput();
        return currentOutput == null ||
                (this.MANA_POTION.isSimilar(currentOutput) &&
                        currentOutput.getAmount() < currentOutput.getMaxStackSize());
    }

    private void generateOutput() {
        ItemStack currentOutput = this.getCurrentOutput();
        ItemStack updatedOutput;
        if (currentOutput == null) {
            updatedOutput = new ManaPotion().getItem();
        } else {
            currentOutput.setAmount(currentOutput.getAmount() + 1);
            updatedOutput = currentOutput;
        }
        this.inventory.setItem(this.OUTPUT_INVENTORY_INDEX, updatedOutput);

    }

    @Override
    protected void dropInventoryItems() {
        this.tryDropFromInventory(this.INPUT_INVENTORY_INDEX);
        this.tryDropFromInventory(this.OUTPUT_INVENTORY_INDEX);
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
        return new ManaBottler().getItem();
    }

    @Override
    public boolean canReceiveItemsFromHopper() {
        return false;
    }

    @Override
    public void tryGetFromExternalInventory(Inventory otherInventory) {
    }

    @Override
    protected ArrayList<Integer> getIndexesToAvoidInventorySerialization() {
        return this.machineEngine.getIndexesToAvoidInventorySerialization();
    }

    private void createInventory() {
        this.machineEngine.createInventory(this.INVENTORY_SIZE, new int[]{
                this.INPUT_INVENTORY_INDEX,
                this.OUTPUT_INVENTORY_INDEX,
                this.PROGRESS_BAR_INVENTORY_INDEX
        });
        this.updateProgressBar();
    }

    @Override
    public ManaTank getTank() {
        return this.tank;
    }

    @Override
    public String getName() {
        return "Embotellador de maná";
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public ItemStack[] getValidBuffs() {
        return new ItemStack[0];
    }

    private void updateProgressBar() {
        this.getInventory().setItem(this.PROGRESS_BAR_INVENTORY_INDEX,
                ProgressBar.generateAtPercentage(this.completedPercentage));
    }

    public CustomBlockStateSerialized serialize() {
        CustomBlockStateSerialized serialized = super.serialize();
        serialized.put("completedPercentage", this.gson.toJson(this.completedPercentage));
        serialized.put("inProgress", this.gson.toJson(this.inProgress));
        serialized.put("currentMana", this.gson.toJson(this.tank.getCurrentMana()));
        return serialized;
    }

    public static ManaBottlerBlockState deserialize(CustomBlockStateSerialized serialized) {
        ManaBottlerBlockState state = new ManaBottlerBlockState(
                deserializeOwnerPlayerName(serialized),
                deserializeWorld(serialized),
                deserializeLocation(serialized)
        );
        state.populateInventoryFromSerialized(serialized);
        state.completedPercentage = gson.fromJson(serialized.get("completedPercentage"), Integer.class);
        state.inProgress = gson.fromJson(serialized.get("inProgress"), Boolean.class);
        int currentMana = gson.fromJson(serialized.get("currentMana"), Integer.class);
        state.tank = new ManaTank(state.MAX_MANA_CAPACITY, currentMana);
        return state;
    }

    @Override
    public ManaImporter getManaImporter() {
        return this.manaImporter;
    }
}
