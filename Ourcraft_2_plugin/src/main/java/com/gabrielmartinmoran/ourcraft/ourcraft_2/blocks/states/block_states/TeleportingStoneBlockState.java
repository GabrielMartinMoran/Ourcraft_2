package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.CustomBlockStateProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.helpers.ManaMachineEngine;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input.ManaImporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaConsumer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.TeleportingStone;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.LockedSlot;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.SlotValidation;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.TeleportButton;
import jline.internal.Nullable;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TeleportingStoneBlockState extends CustomBlockState implements ManaConsumer {

    private ManaImporter manaImporter;
    private ManaMachineEngine machineEngine;
    private ManaTank tank;
    @Nullable
    private String linkName;
    private final int INVENTORY_SIZE = 36;
    private final int MAX_MANA_CAPACITY = 200;
    private final int MANA_EXTRACTION_RATE = 50;
    private final double MANA_EXTRACTOR_RANGE = 10;
    private final int TELEPORT_MANA_COST = 100;
    private final int LINK_NAME_INVENTORY_INDEX = 30;
    private final int LINK_NAME_VALIDATOR_INVENTORY_INDEX = 31;
    private final int TRAVEL_BUTTON_INVENTORY_INDEX = 32;
    private final ItemStack TELEPORT_BUTTON = TeleportButton.generate(TELEPORT_MANA_COST);

    public TeleportingStoneBlockState(String ownerPlayerName, World world, Location location) {
        super(ownerPlayerName, world, location);
        this.tank = new ManaTank(this.MAX_MANA_CAPACITY, 0);
        this.manaImporter = new ManaImporter(this.location, this.MANA_EXTRACTOR_RANGE, this.MANA_EXTRACTION_RATE);
        this.machineEngine = new ManaMachineEngine(this);
        this.linkName = null;
        this.createInventory();
    }

    @Override
    public void onTick() {
        this.updateLight();
        this.machineEngine.onTick();
        this.validateLinkName();
    }

    private void validateLinkName() {
        ItemStack linkNameItem = this.getLinkNameItem();
        ItemStack validator;
        if (linkNameItem == null) {
            validator = LockedSlot.generate();
            this.linkName = null;
        } else if (linkNameItem.getType() == Material.PAPER) {
            validator = SlotValidation.generateValid();
            this.linkName = this.getLinkName();
        } else {
            validator = SlotValidation.generateInvalid();
            this.linkName = null;
        }
        this.inventory.setItem(this.LINK_NAME_VALIDATOR_INVENTORY_INDEX, validator);
    }

    @Override
    protected void dropInventoryItems() {
        this.tryDropFromInventory(this.LINK_NAME_INVENTORY_INDEX);
        for (int index : this.machineEngine.getDropIndexes()) {
            this.tryDropFromInventory(index);
        }
    }

    @Override
    public void onInteract(Player player) {
        player.openInventory(this.getInventory());
    }

    public void onInventoryItemClick(InventoryClickEvent event) {
        super.onInventoryItemClick(event);
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().isSimilar(this.TELEPORT_BUTTON)) {
            if (this.linkName == null) {
                event.getWhoClicked().sendMessage("" + ChatColor.RED + "Esta piedra de teletransporte no tiene un " +
                        "nombre para ser asociada a otra. " + "Para poder utilizarla agregue al inventario de la " +
                        "piedra de teletransporte un papel " + "renombrado que representará el nombre de la misma. La" +
                        " piedra se conectará con las que " + "tengan el mismo nombre.");
                event.setCancelled(true);
                return;
            }
            TeleportingStoneBlockState otherState = this.getLinkedTeleporter();
            if (otherState != null) {
                if (this.tank.hasEnough(this.TELEPORT_MANA_COST)) {
                    this.tank.subtract(this.TELEPORT_MANA_COST);
                    this.teleport(event.getWhoClicked(), otherState);
                } else {
                    event.getWhoClicked().sendMessage("" + ChatColor.RED + "No hay suficiente mana para realizar la " +
                            "teletransportación.");
                }
            } else {
                event.getWhoClicked().sendMessage("" + ChatColor.RED + "Esta piedra de teletransporte no esta " +
                        "asociada a ninguna otra.");
            }
            event.setCancelled(true);
        }
    }

    private void teleport(HumanEntity entity, TeleportingStoneBlockState otherTeleporter) {
        entity.closeInventory();
        entity.teleport(otherTeleporter.getLocation());
        this.getWorld().playSound(this.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        entity.getWorld().playSound(otherTeleporter.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        this.getWorld().spawnParticle(Particle.PORTAL, this.getLocation().clone().add(0.5d, 0, 0.5d), 200);
        entity.getWorld().spawnParticle(Particle.PORTAL, otherTeleporter.getLocation().clone().add(0.5d, 0, 0.5d), 200);
    }

    @Nullable
    private TeleportingStoneBlockState getLinkedTeleporter() {
        ArrayList<TeleportingStoneBlockState> linkedTeleporters = new ArrayList<>();
        for (CustomBlockState state : CustomBlockStateProvider.all()) {
            if (state instanceof TeleportingStoneBlockState && state != this && this.linkName.equals(((TeleportingStoneBlockState) state).getLinkName())) {
                linkedTeleporters.add((TeleportingStoneBlockState) state);
            }
        }
        if (linkedTeleporters.isEmpty()) return null;
        int randomIndex = ThreadLocalRandom.current().nextInt(0, linkedTeleporters.size());
        return linkedTeleporters.get(randomIndex);
    }

    @Override
    public ItemStack getBlockItem() {
        return new TeleportingStone().getItem();
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

    public static TeleportingStoneBlockState deserialize(CustomBlockStateSerialized serialized) {
        TeleportingStoneBlockState state = new TeleportingStoneBlockState(
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
                this.LINK_NAME_INVENTORY_INDEX,
                this.TRAVEL_BUTTON_INVENTORY_INDEX
        });
        this.inventory.setItem(this.TRAVEL_BUTTON_INVENTORY_INDEX, TELEPORT_BUTTON);
    }

    @Override
    public ManaTank getTank() {
        return this.tank;
    }

    @Override
    public String getName() {
        return "Piedra de teletransporte";
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public ItemStack[] getValidBuffs() {
        return new ItemStack[0];
    }

    @Nullable
    public ItemStack getLinkNameItem() {
        return this.getInventory().getItem(this.LINK_NAME_INVENTORY_INDEX);
    }

    @Nullable
    public String getLinkName() {
        ItemStack item = this.getLinkNameItem();
        if (item == null) return null;
        return item.getItemMeta().getDisplayName();
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
