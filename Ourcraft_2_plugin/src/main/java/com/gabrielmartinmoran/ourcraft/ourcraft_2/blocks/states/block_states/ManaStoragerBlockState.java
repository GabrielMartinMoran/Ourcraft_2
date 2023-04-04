package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.helpers.ManaMachineEngine;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input.ManaImporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaConsumer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.ManaExporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.ManaPool;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.ManaStorager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ManaStoragerBlockState extends CustomBlockState implements ManaProvider, ManaConsumer {

    private ManaPool manaPool;
    private ManaImporter manaImporter;
    private ManaMachineEngine machineEngine;
    private ManaTank tank;
    private final int MAX_MANA_CAPACITY = 5000;
    private final int MANA_EXTRACTION_RATE = 10;
    private final double MANA_EXTRACTOR_RANGE = 10;

    public ManaStoragerBlockState(String ownerPlayerName, World world, Location location) {
        super(ownerPlayerName, world, location);
        this.tank = new ManaTank(this.MAX_MANA_CAPACITY, 0);
        this.manaPool = new ManaPool(this.getName(), location, this.tank);
        this.machineEngine = new ManaMachineEngine(this);
        this.manaImporter = new ManaImporter(location, this.MANA_EXTRACTOR_RANGE, this.MANA_EXTRACTION_RATE);
        this.machineEngine.createInventory();

    }

    public CustomBlockStateSerialized serialize() {
        CustomBlockStateSerialized serialized = super.serialize();
        serialized.put("currentMana", this.gson.toJson(this.tank.getCurrentMana()));
        return serialized;
    }

    public static ManaStoragerBlockState deserialize(CustomBlockStateSerialized serialized) {
        ManaStoragerBlockState state = new ManaStoragerBlockState(
                deserializeOwnerPlayerName(serialized),
                deserializeWorld(serialized),
                deserializeLocation(serialized)
        );
        state.populateInventoryFromSerialized(serialized);
        int currentMana = gson.fromJson(serialized.get("currentMana"), Integer.class);
        state.tank = new ManaTank(state.MAX_MANA_CAPACITY, currentMana);
        state.manaPool = new ManaPool(state.getName(), state.location, state.tank);
        return state;
    }

    @Override
    public void onTick() {
        this.updateLight();
        this.machineEngine.onTick();
    }

    @Override
    protected void dropInventoryItems() {
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
        return new ManaStorager().getItem();
    }

    @Override
    public boolean canReceiveItemsFromHopper() {
        return false;
    }

    @Override
    public void tryGetFromExternalInventory(Inventory otherInventory) {
    }

    @Override
    public ManaTank getTank() {
        return this.tank;
    }

    public String getName() {
        return "Almacenador de maná";
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
    public ManaImporter getManaImporter() {
        return this.manaImporter;
    }

    @Override
    protected ArrayList<Integer> getIndexesToAvoidInventorySerialization() {
        return this.machineEngine.getIndexesToAvoidInventorySerialization();
    }
}
