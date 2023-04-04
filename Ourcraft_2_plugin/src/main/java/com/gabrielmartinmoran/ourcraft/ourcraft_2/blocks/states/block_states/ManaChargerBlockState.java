package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.helpers.ManaMachineEngine;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input.ManaImporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaConsumer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.ManaBattery;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.ManaCharger;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ManaChargerBlockState extends CustomBlockState implements ManaConsumer {

    private ManaImporter manaImporter;
    private ManaMachineEngine machineEngine;
    private ManaTank tank;
    private final int INVENTORY_SIZE = 36;
    private final int MAX_MANA_CAPACITY = 200;
    private final int MANA_EXTRACTION_RATE = 50;
    private final double MANA_EXTRACTOR_RANGE = 10;
    private final int ITEM_TO_CHARGE_INVENTORY_INDEX = 31;
    private final int MANA_CHARGED_PER_SECOND = 50;

    public ManaChargerBlockState(String ownerPlayerName, World world, Location location) {
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
        this.tryChargeDevice();
        this.machineEngine.updateTank();
    }

    private void tryChargeDevice() {
        ItemStack itemToCharge = this.getInventory().getItem(this.ITEM_TO_CHARGE_INVENTORY_INDEX);
        if (ManaBattery.isBattery(itemToCharge)) {
            int maxCapacity = ManaBattery.getMaxManaCapacity(itemToCharge);
            int currentMana = ManaBattery.getCurrentMana(itemToCharge);
            int remainingCapacity = maxCapacity - currentMana;
            if (remainingCapacity == 0) return;
            int manaWanted = Math.min(remainingCapacity, this.MANA_CHARGED_PER_SECOND);
            int manaToTransfer = this.tank.subtractAllPossible(manaWanted);
            if (manaToTransfer == 0) return;
            currentMana += manaToTransfer;
            ManaBattery.setCurrentMana(itemToCharge, currentMana);
        }
    }

    @Override
    protected void dropInventoryItems() {
        this.tryDropFromInventory(this.ITEM_TO_CHARGE_INVENTORY_INDEX);
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
        return new ManaCharger().getItem();
    }

    @Override
    public boolean canReceiveItemsFromHopper() {
        return false;
    }

    @Override
    public void tryGetFromExternalInventory(Inventory otherInventory) {
    }

    public CustomBlockStateSerialized serialize() {
        CustomBlockStateSerialized serialized = super.serialize();
        serialized.put("currentMana", this.gson.toJson(this.tank.getCurrentMana()));
        return serialized;
    }

    public static ManaChargerBlockState deserialize(CustomBlockStateSerialized serialized) {
        ManaChargerBlockState state = new ManaChargerBlockState(
                deserializeOwnerPlayerName(serialized),
                deserializeWorld(serialized),
                deserializeLocation(serialized)
        );
        state.populateInventoryFromSerialized(serialized);
        int currentMana = gson.fromJson(serialized.get("currentMana"), Integer.class);
        state.tank = new ManaTank(state.MAX_MANA_CAPACITY, currentMana);
        return state;
    }

    private void createInventory() {
        this.machineEngine.createInventory(this.INVENTORY_SIZE, new int[]{
                this.ITEM_TO_CHARGE_INVENTORY_INDEX
        });
    }

    @Override
    public ManaTank getTank() {
        return this.tank;
    }

    @Override
    public String getName() {
        return "Cargador de maná";
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
    public ManaImporter getManaImporter() {
        return this.manaImporter;
    }

    @Override
    protected ArrayList<Integer> getIndexesToAvoidInventorySerialization() {
        return this.machineEngine.getIndexesToAvoidInventorySerialization();
    }
}
