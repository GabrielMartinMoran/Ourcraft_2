package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.helpers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input.ManaImporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaConsumer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaMachine;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.ManaExporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.LockedSlot;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.ManaContainerItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.ManaLinkedIndicator;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.SlotValidation;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.manachannels.ManaReceptionChannel;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.manachannels.ManaTransmissionChannel;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories.InventoryBuilder;
import jline.internal.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ManaMachineEngine {

    private ManaMachine manaMachine;

    private final int MIN_INVENTORY_SIZE = 27;
    private final int CONNECTION_STATE_INVENTORY_INDEX = 0;
    private final int MANA_TANK_TOP_INVENTORY_INDEX = 9;
    private final int MANA_TANK_BOTTOM_INVENTORY_INDEX = 18;
    private final int MANA_RECEPTION_CHANNEL_UI_INVENTORY_INDEX = 10;
    private final int MANA_RECEPTION_CHANNEL_ITEM_INVENTORY_INDEX = 19;
    private final int MANA_TRANSMISSION_CHANNEL_UI_INVENTORY_INDEX = 17;
    private final int MANA_TRANSMISSION_CHANNEL_ITEM_INVENTORY_INDEX = 26;

    private final int BUFF_1_INVENTORY_INDEX = 11;
    private final int BUFF_1_VALIDATOR_INVENTORY_INDEX = 12;
    private final int BUFF_2_INVENTORY_INDEX = 13;
    private final int BUFF_2_VALIDATOR_INVENTORY_INDEX = 14;
    private final int BUFF_3_INVENTORY_INDEX = 15;
    private final int BUFF_3_VALIDATOR_INVENTORY_INDEX = 16;


    public ManaMachineEngine(ManaMachine manaMachine) {
        this.manaMachine = manaMachine;
    }

    public void createInventory() {
        this.createInventory(this.MIN_INVENTORY_SIZE);
    }

    public void createInventory(int size) {
        this.createInventory(size, new int[0]);
    }

    public void createInventory(int[] unlockedSlotsIndexes) {
        this.createInventory(this.MIN_INVENTORY_SIZE, unlockedSlotsIndexes);
    }

    public void createInventory(int size, int[] unlockedSlotsIndexes) {
        if (size < this.MIN_INVENTORY_SIZE) {
            throw new AssertionError(String.format("Inventory size must be at least %s slots.",
                    this.MIN_INVENTORY_SIZE));
        }
        Inventory inventory = InventoryBuilder.build(size, this.manaMachine.getName());
        ArrayList<Integer> toAvoidFill = new ArrayList<>();
        for (int index : unlockedSlotsIndexes) toAvoidFill.add(index);
        for (int index : new int[]{
                this.BUFF_1_INVENTORY_INDEX,
                this.BUFF_2_INVENTORY_INDEX,
                this.BUFF_3_INVENTORY_INDEX
        })
            toAvoidFill.add(index);
        for (int i = 0; i < size; i++) {
            if (!toAvoidFill.contains(i)) {
                inventory.setItem(i, LockedSlot.generate());
            }
        }
        if (this.getManaImporter() != null) {
            inventory.setItem(this.MANA_RECEPTION_CHANNEL_UI_INVENTORY_INDEX,
                    ManaReceptionChannel.generate());
            inventory.setItem(this.MANA_RECEPTION_CHANNEL_ITEM_INVENTORY_INDEX, null);
        }
        if (this.getManaExporter() != null) {
            inventory.setItem(this.MANA_TRANSMISSION_CHANNEL_UI_INVENTORY_INDEX,
                    ManaTransmissionChannel.generate());
            inventory.setItem(this.MANA_TRANSMISSION_CHANNEL_ITEM_INVENTORY_INDEX, null);
        }
        this.manaMachine.setInventory(inventory);
        this.updateTank();
    }


    public void onTick() {
        this.onTick(true);
    }

    public void onTick(boolean updateTank) {
        this.validateBuffs();
        if (this.getManaImporter() != null) {
            this.checkExtractorConnection();
            if (this.getManaImporter().isConnected()) {
                if (this.getTank() != null && this.getTank().hasSpace()) {
                    int extracted =
                            this.getManaImporter().extractAllPossible(Math.min(this.getManaImporter().getExtractionRate(), this.getTank().getSpace()));
                    this.getTank().add(extracted);
                }
            }
        }
        if (this.getManaExporter() != null) {
            this.getManaExporter().setTransmissionChannel(this.getTransmissionChannel());
        }
        if (updateTank) {
            this.updateTank();
        }
    }

    private void validateBuffs() {
        this.validateBuff(this.BUFF_1_INVENTORY_INDEX, this.BUFF_1_VALIDATOR_INVENTORY_INDEX);
        this.validateBuff(this.BUFF_2_INVENTORY_INDEX, this.BUFF_2_VALIDATOR_INVENTORY_INDEX);
        this.validateBuff(this.BUFF_3_INVENTORY_INDEX, this.BUFF_3_VALIDATOR_INVENTORY_INDEX);
    }

    public void validateBuff(int index, int validatorIndex) {
        ItemStack item = this.manaMachine.getInventory().getItem(index);
        if (item == null) {
            this.manaMachine.getInventory().setItem(validatorIndex, LockedSlot.generate());
        } else if (this.isValidBuff(item)) {
            this.manaMachine.getInventory().setItem(validatorIndex, SlotValidation.generateValid());
        } else {
            this.manaMachine.getInventory().setItem(validatorIndex, SlotValidation.generateInvalid());
        }

    }

    private boolean isValidBuff(ItemStack item) {
        for (ItemStack validBuff : this.manaMachine.getValidBuffs()) {
            if (validBuff.isSimilar(item)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ItemStack> getBuffs() {
        ArrayList<ItemStack> buffs = new ArrayList<>();
        for (int index : new int[]{this.BUFF_1_INVENTORY_INDEX, this.BUFF_2_INVENTORY_INDEX,
                this.BUFF_3_INVENTORY_INDEX}) {
            ItemStack buff = this.manaMachine.getInventory().getItem(index);
            if (this.isValidBuff(buff)) {
                buffs.add(buff);
            }
        }
        return buffs;
    }


    @Nullable
    private ItemStack getTransmissionChannel() {
        if (this.getManaExporter() == null) return null;
        return this.manaMachine.getInventory().getItem(this.MANA_TRANSMISSION_CHANNEL_ITEM_INVENTORY_INDEX);
    }

    @Nullable
    private ItemStack getReceptionChannel() {
        if (this.getManaImporter() == null) return null;
        return this.manaMachine.getInventory().getItem(this.MANA_RECEPTION_CHANNEL_ITEM_INVENTORY_INDEX);
    }

    public void checkExtractorConnection() {
        this.getManaImporter().setReceptionChannel(this.getReceptionChannel());
        this.getManaImporter().refreshConnection();
        ItemStack item;
        if (this.getManaImporter().isConnected()) {
            item = ManaLinkedIndicator.generateConnected();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(Arrays.asList(new String[]{String.format("Conectado a %s",
                    this.getManaImporter().getConnectedManaExporter().getName()), String.format("en X:%s Y:%s Z:%s",
                    this.getManaImporter().getConnectedManaExporter().getLocation().getBlockX(),
                    this.getManaImporter().getConnectedManaExporter().getLocation().getBlockY(),
                    this.getManaImporter().getConnectedManaExporter().getLocation().getBlockZ())}));
            item.setItemMeta(itemMeta);
        } else {
            item = ManaLinkedIndicator.generateDisconnected();
        }
        this.manaMachine.getInventory().setItem(this.CONNECTION_STATE_INVENTORY_INDEX, item);
    }


    public void updateTank() {
        if (this.getTank() == null) return;
        this.manaMachine.getInventory().setItem(this.MANA_TANK_TOP_INVENTORY_INDEX,
                ManaContainerItem.generateTop(this.getTank().getCurrentMana(), this.getTank().getMaxMana()));
        this.manaMachine.getInventory().setItem(this.MANA_TANK_BOTTOM_INVENTORY_INDEX,
                ManaContainerItem.generateBottom(this.getTank().getCurrentMana(), this.getTank().getMaxMana()));
    }

    public ArrayList<Integer> getDropIndexes() {
        ArrayList<Integer> indexes = new ArrayList<>();
        if (this.getManaImporter() != null) {
            indexes.add(this.MANA_RECEPTION_CHANNEL_ITEM_INVENTORY_INDEX);
        }
        if (this.getManaExporter() != null) {
            indexes.add(this.MANA_TRANSMISSION_CHANNEL_ITEM_INVENTORY_INDEX);
        }
        indexes.add(this.BUFF_1_INVENTORY_INDEX);
        indexes.add(this.BUFF_2_INVENTORY_INDEX);
        indexes.add(this.BUFF_3_INVENTORY_INDEX);
        return indexes;
    }

    @Nullable
    private ManaImporter getManaImporter() {
        if (manaMachine instanceof ManaConsumer) {
            return ((ManaConsumer) manaMachine).getManaImporter();
        }
        return null;
    }

    @Nullable
    private ManaExporter getManaExporter() {
        if (manaMachine instanceof ManaProvider) {
            return ((ManaProvider) manaMachine).getManaExporter();
        }
        return null;
    }

    @Nullable
    private ManaTank getTank() {
        return this.manaMachine.getTank();
    }

    public ArrayList<Integer> getIndexesToAvoidInventorySerialization() {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < this.manaMachine.getInventory().getContents().length; i++) {
            ItemStack item = this.manaMachine.getInventory().getItem(i);
            if (item != null && item.getType() == Material.BARRIER) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}
