package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.helpers.ManaMachineEngine;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input.ManaImporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaConsumer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.ManaExporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.RedirectedManaPool;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.ManaRepeater;
import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ManaRepeaterBlockState extends CustomBlockState implements ManaProvider, ManaConsumer {


    private ManaImporter manaImporter;
    private RedirectedManaPool redirectedManaPool;
    private ManaMachineEngine machineEngine;
    private final int MANA_EXTRACTOR_RANGE = 10;

    public ManaRepeaterBlockState(String ownerPlayerName, World world, Location location) {
        super(ownerPlayerName, world, location);
        this.manaImporter = new ManaImporter(this.location, this.MANA_EXTRACTOR_RANGE, 0);
        this.redirectedManaPool = new RedirectedManaPool(this.getName(), this.location);
        this.machineEngine = new ManaMachineEngine(this);
        this.machineEngine.createInventory();
    }

    public static ManaRepeaterBlockState deserialize(CustomBlockStateSerialized serialized) {
        ManaRepeaterBlockState state = new ManaRepeaterBlockState(
                deserializeOwnerPlayerName(serialized),
                deserializeWorld(serialized),
                deserializeLocation(serialized)
        );
        state.populateInventoryFromSerialized(serialized);
        return state;
    }

    @Override
    public ManaTank getTank() {
        return this.redirectedManaPool.getTank();
    }

    public String getName() {
        return "Repetidor de maná";
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
    public void onTick() {
        this.updateLight();
        this.machineEngine.onTick();
        if (this.manaImporter.isConnected()) {
            ManaExporter extractorExporter = this.manaImporter.getConnectedManaExporter();
            this.redirectedManaPool.setManaExporter(extractorExporter);
        } else {
            this.redirectedManaPool.setManaExporter(null);
        }
    }

    @Override
    protected void dropInventoryItems() {
        for (int index : this.machineEngine.getDropIndexes()) {
            this.tryDropFromInventory(index);
        }
    }

    @Override
    public void onInteract(Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    public ItemStack getBlockItem() {
        return new ManaRepeater().getItem();
    }

    @Override
    public boolean canReceiveItemsFromHopper() {
        return false;
    }

    @Override
    public void tryGetFromExternalInventory(Inventory otherInventory) {

    }

    @Override
    public ManaImporter getManaImporter() {
        return this.manaImporter;
    }

    @Override
    @Nullable
    public ManaExporter getManaExporter() {
        return this.redirectedManaPool;
    }

    @Override
    protected ArrayList<Integer> getIndexesToAvoidInventorySerialization() {
        return this.machineEngine.getIndexesToAvoidInventorySerialization();
    }
}
