package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.filters.ManaTurretTargetsFilter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.helpers.ManaMachineEngine;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input.ManaImporter;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaConsumer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.ManaTurret;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.DistanceTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.PowerTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.SpeedupTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.LockedSlot;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.SlotValidation;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsResolver;
import jline.internal.Nullable;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ManaTurretBlockState extends CustomBlockState implements ManaConsumer {

    private boolean validTurretType;
    private int cooldown;
    private ManaImporter manaImporter;
    private ManaMachineEngine machineEngine;
    private ManaTank tank;

    private final int INVENTORY_SIZE = 36;
    private final int CUSTOM_UI_INVENTORY_INDEX = 0;
    private final int TARGET_FILTER_INVENTORY_INDEX = 28;
    private final int TARGET_FILTER_VALIDATOR_INVENTORY_INDEX = 29;
    private final int TURRET_TYPE_INVENTORY_INDEX = 31;
    private final int TURRET_TYPE_VALIDATOR_INVENTORY_INDEX = 32;

    private final double BASE_TARGET_RANGE = 10;
    private final int BASE_COOLDOWN = 5;
    private final int BASE_SHOOT_COST = 25;
    private final int COST_PER_BUFF = 15;
    private static final ItemStack DISTANCE_BUFF = new DistanceTablet().getItem();
    private static final ItemStack POWER_BUFF = new PowerTablet().getItem();
    private static final ItemStack SPEED_BUFF = new SpeedupTablet().getItem();
    private static final ItemStack[] VALID_BUFFS = new ItemStack[]{DISTANCE_BUFF, POWER_BUFF, SPEED_BUFF};
    private final double MANA_EXTRACTOR_RANGE = 10;

    private final int MAX_MANA_CAPACITY = 200;
    private final int MANA_EXTRACTION_RATE = 50;


    private final List<Material> VALID_TURRET_TYPE_ITEMS = Arrays.asList(new Material[]{Material.CROSSBOW,
            Material.ENCHANTED_GOLDEN_APPLE});

    public ManaTurretBlockState(String ownerPlayerName, World world, Location location) {
        super(ownerPlayerName, world, location);
        this.tank = new ManaTank(this.MAX_MANA_CAPACITY, 0);
        this.manaImporter = new ManaImporter(this.location, this.MANA_EXTRACTOR_RANGE, this.MANA_EXTRACTION_RATE);
        this.cooldown = 0;
        this.machineEngine = new ManaMachineEngine(this);
        this.createInventory();
    }

    @Override
    public void onTick() {
        this.updateLight();
        this.machineEngine.onTick();
        this.validateFilter();
        this.validateTurretType();
        if (this.getCooldown() > 0) {
            this.setCooldown(this.getCooldown() - 1);
        } else if (this.validTurretType) {
            this.tryShoot();
        }
    }

    private void tryShoot() {
        int distanceAugments = 0;
        int powerAugments = 0;
        int speedAugments = 0;
        for (ItemStack buff : this.getValidBuffs()) {
            if (DISTANCE_BUFF.isSimilar(buff)) {
                distanceAugments++;
            } else if (POWER_BUFF.isSimilar(buff)) {
                powerAugments++;
            } else if (SPEED_BUFF.isSimilar(buff)) {
                speedAugments++;
            }
        }
        int shootCost = this.calculateShootCost();
        if (!this.tank.hasEnough(shootCost)) return;
        LivingEntity target =
                this.getNearestTarget(this.BASE_TARGET_RANGE + this.BASE_TARGET_RANGE * (0.5f * distanceAugments));
        if (target == null) return;
        this.tank.subtract(shootCost);
        this.setCooldown((int) (this.BASE_COOLDOWN - this.BASE_COOLDOWN * (0.25f * speedAugments)));
        this.shoot(target, distanceAugments, powerAugments);
    }

    private void shoot(LivingEntity target, int distanceAugments, int powerAugments) {
        ItemStack turretTypeItem = this.getTurretTypeItem();
        if (turretTypeItem.getType() == Material.CROSSBOW) {
            this.getWorld().playSound(this.getItemDisplayLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
            Location location = this.getItemDisplayLocation().clone().add(0, 2, 0);
            Vector direction = target.getLocation().toVector().add(new Vector(0, 2, 0)).subtract(location.toVector());
            Arrow arrow = this.getWorld().spawnArrow(location, direction, 2f + (distanceAugments * 0.5f), 0);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            int spellLevel = 1 + powerAugments;
            SpellsResolver.tagProjectile(arrow, SpellTypes.MAGIC_SHOT, spellLevel);
            return;
        }
        if (turretTypeItem.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            target.getWorld().playSound(target.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1f, 0.5f);
            int duration = ((1 + powerAugments) * 2) * Config.TICKS_PER_SECOND;
            target.getWorld().spawnParticle(Particle.CRIMSON_SPORE, target.getLocation(), 100);
            PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, duration, 2);
            target.addPotionEffect(effect);
        }
    }

    private int calculateShootCost() {
        return this.BASE_SHOOT_COST + this.COST_PER_BUFF * this.getValidBuffs().length;
    }

    @Nullable
    private LivingEntity getNearestTarget(double maxDistance) {
        ManaTurretTargetsFilter targetsFilter = this.getTargetsFilter();
        Collection<Entity> result = this.getWorld().getNearbyEntities(this.getLocation(), maxDistance, maxDistance,
                maxDistance, e -> e instanceof LivingEntity && targetsFilter.shouldTarget((LivingEntity) e));
        if (result.isEmpty()) return null;
        List<LivingEntity> entities = Arrays.asList(result.toArray(new LivingEntity[result.size()]));
        entities.sort((e1, e2) -> {
            Double a = this.getLocation().distance(e1.getLocation());
            Double b = this.getLocation().distance(e2.getLocation());
            return a.compareTo(b);
        });
        return entities.get(0);
    }

    private ManaTurretTargetsFilter getTargetsFilter() {
        ItemStack filter = this.getTargetFilter();
        if (filter == null || !(filter.getItemMeta() instanceof BookMeta)) {
            // If there is no filter, add the owner as the only whitelisted
            ArrayList<String> whitelist = new ArrayList<>();
            whitelist.add(this.getOwnerPlayerName());
            ArrayList<String> blacklist = new ArrayList<>();
            return new ManaTurretTargetsFilter(whitelist, blacklist);
        }
        return ManaTurretTargetsFilter.fromBookMeta((BookMeta) filter.getItemMeta());
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

    public void onInventoryItemClick(InventoryClickEvent event) {
        super.onInventoryItemClick(event);
        if (event.getCurrentItem() == null) return;
    }

    @Nullable
    public ItemStack getTargetFilter() {
        return this.getInventory().getItem(this.TARGET_FILTER_INVENTORY_INDEX);
    }

    private void validateFilter() {
        ItemStack filter = this.getTargetFilter();
        ItemStack validator;
        if (filter == null) {
            validator = LockedSlot.generate();
        } else if (filter.getItemMeta() instanceof BookMeta) {
            validator = SlotValidation.generateValid();
        } else {
            validator = SlotValidation.generateInvalid();
        }
        this.inventory.setItem(this.TARGET_FILTER_VALIDATOR_INVENTORY_INDEX, validator);
    }

    @Nullable
    public ItemStack getTurretTypeItem() {
        return this.getInventory().getItem(this.TURRET_TYPE_INVENTORY_INDEX);
    }

    private void validateTurretType() {
        ItemStack turretType = this.getTurretTypeItem();
        ItemStack validator;
        if (turretType == null) {
            validator = LockedSlot.generate();
            this.validTurretType = false;
        } else if (this.VALID_TURRET_TYPE_ITEMS.contains(turretType.getType())) {
            validator = SlotValidation.generateValid();
            this.validTurretType = true;
        } else {
            validator = SlotValidation.generateInvalid();
            this.validTurretType = false;
        }
        this.inventory.setItem(this.TURRET_TYPE_VALIDATOR_INVENTORY_INDEX, validator);
    }

    @Override
    public ItemStack getBlockItem() {
        return new ManaTurret().getItem();
    }

    public ItemStack[] getValidBuffs() {
        return this.VALID_BUFFS;
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

    @Override
    public String getName() {
        return "Torreta de maná";
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    private void createInventory() {
        this.machineEngine.createInventory(this.INVENTORY_SIZE, new int[]{
                //this.CUSTOM_UI_INVENTORY_INDEX,
                this.TURRET_TYPE_INVENTORY_INDEX,
                this.TARGET_FILTER_INVENTORY_INDEX
        });
        //this.inventory.setItem(this.CUSTOM_UI_INVENTORY_INDEX, ManaTurretUI.generate());
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public CustomBlockStateSerialized serialize() {
        CustomBlockStateSerialized serialized = super.serialize();
        serialized.put("cooldown", this.gson.toJson(this.getCooldown()));
        serialized.put("currentMana", this.gson.toJson(this.tank.getCurrentMana()));
        return serialized;
    }

    public static ManaTurretBlockState deserialize(CustomBlockStateSerialized serialized) {
        ManaTurretBlockState state = new ManaTurretBlockState(
                deserializeOwnerPlayerName(serialized),
                deserializeWorld(serialized),
                deserializeLocation(serialized)
        );
        state.populateInventoryFromSerialized(serialized);
        state.setCooldown(gson.fromJson(serialized.get("cooldown"), Integer.class));
        int currentMana = gson.fromJson(serialized.get("currentMana"), Integer.class);
        state.tank = new ManaTank(state.MAX_MANA_CAPACITY, currentMana);
        return state;
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
