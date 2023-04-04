package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs.CustomMob;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.DifficultyZone;
import com.google.gson.Gson;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomMobsManager {

    private SecureRandom rand;
    private Gson gson;
    private HashMap<Integer, HashMap<EntityType, MobReplacement[]>> overworldReplacementsMap;
    private HashMap<EntityType, MobReplacement[]> netherReplacementsMap;
    private HashMap<EntityType, MobReplacement[]> endReplacementsMap;

    public CustomMobsManager() {
        this.rand = new SecureRandom();
        this.gson = new Gson();
        this.loadOverworldReplacements();
        this.loadNetherReplacements();
        this.loadEndReplacements();
    }

    public void replaceIfNeeded(LivingEntity mob) {
        MobReplacement replacement = this.getReplacement(mob);
        if (replacement == null || replacement.getMobType() == CustomMobsTypes.NONE) return;
        try {
            replacement.spawn(mob.getWorld(), mob.getLocation());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al tratar de spawnear el mob " + replacement.getMobType());
            return;
        }
        mob.remove();
    }

    public boolean isCustomMob(LivingEntity entity) {
        List<MetadataValue> metadata = entity.getMetadata(CustomMob.IS_CUSTOM_MOB_TAG);
        return metadata.size() > 0 && metadata.get(0).asBoolean();
    }

    public boolean hasCustomDrop(LivingEntity entity) {
        CustomMobsTypes type = this.getCustomMobType(entity);
        if (type.equals(CustomMobsTypes.NONE)) return false;
        return type.getCustomMob().hasCustomDrops();
    }

    private MobReplacement getReplacement(LivingEntity mob) {
        World.Environment worldEnv = mob.getWorld().getEnvironment();
        if (worldEnv.equals(World.Environment.NORMAL)) return this.getOverworldReplacement(mob);
        if (worldEnv.equals(World.Environment.NETHER)) return this.getNetherReplacement(mob);
        if (worldEnv.equals(World.Environment.THE_END)) return this.getEndReplacement(mob);
        return null;
    }

    private int getLootingModifier(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item != null) return item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) + 1;
        return 1;
    }

    private float getLuckModifier(Player player) {
        PotionEffect luckPotionEffect = player.getPotionEffect(PotionEffectType.LUCK);
        if (luckPotionEffect != null) return luckPotionEffect.getAmplifier() + 2;
        PotionEffect unluckPotionEffect = player.getPotionEffect(PotionEffectType.UNLUCK);
        if (unluckPotionEffect != null) return (1 / unluckPotionEffect.getAmplifier() + 2);
        return 1;
    }

    private List<MobDrop> getProbabilityTable(List<MobDrop> drops) {
        ArrayList<MobDrop> weightedList = new ArrayList<MobDrop>();
        for (int i = 0; i < drops.size(); i++) {
            for (int j = 0; j < drops.get(i).weight; j++) {
                weightedList.add(drops.get(i));
            }
        }
        return weightedList;
    }

    private void tryAddDropIfBetter(ArrayList<MobDrop> currentDrops, MobDrop drop, int maxDrops) {
        if (maxDrops == 0) return;
        if (currentDrops.size() < maxDrops) {
            currentDrops.add(drop);
            return;
        }
        int lowerIndex = 0;
        int lowerValue = 0;
        MobDrop lower = currentDrops.get(lowerIndex);
        if (maxDrops > 1) {
            for (int i = 1; i < currentDrops.size(); i++) {
                MobDrop current = currentDrops.get(i);
                int currentValue = current.amount * current.value;
                if (current.item == null || lowerValue > currentValue) {
                    lowerIndex = i;
                    lowerValue = currentValue;
                    lower = currentDrops.get(lowerIndex);
                }
            }
        }
        if (drop.item != null && lowerValue < (drop.value * drop.amount)) {
            currentDrops.set(lowerIndex, drop);
        }
    }

    public List<ItemStack> getDrops(LivingEntity entity, Player killer) {
        CustomMobsTypes type = this.getCustomMobType(entity);
        if (type.equals(CustomMobsTypes.NONE)) return new ArrayList<ItemStack>();
        CustomMob customMob = type.getCustomMob();
        int baseRolls = customMob.getCustomDropsRolls();
        List<MobDrop> dropTable = customMob.getCustomDrops();
        int lootingModifier = this.getLootingModifier(killer);
        float luckModifier = this.getLuckModifier(killer);
        int rolls = (int) Math.floor((baseRolls * lootingModifier) * luckModifier);
        int maxDrops = baseRolls + (int) (Math.ceil(Math.sqrt(lootingModifier * luckModifier)) - 1);
        if (maxDrops < baseRolls) maxDrops = baseRolls;
        List<MobDrop> probabilityTable = this.getProbabilityTable(dropTable);
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        for (int i = 0; i < rolls; i++) {
            for (int j = 0; j < maxDrops; j++) {
                MobDrop drop = probabilityTable.get(rand.nextInt(probabilityTable.size()));
                int dropAmount = drop.min + rand.nextInt(drop.max - drop.min + 1);
                /*if (drop.min < drop.max) {
                    int probMax = (int) (drop.max + ((lootingModifier - 1) + (luckModifier - 1)) / 2);
                    dropAmount = (drop.min + rand.nextInt(probMax - drop.min + 1));
                }*/
                MobDrop dropClone = drop.clone();
                dropClone.amount = dropAmount;
                this.tryAddDropIfBetter(drops, dropClone, maxDrops);
            }
        }
        ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
        for (MobDrop drop : drops) {
            if (drop.item == null) continue;
            ItemStack item = drop.item.clone();
            item.setAmount(drop.amount);
            droppedItems.add(item);
        }
        return droppedItems;
    }

    public List<Material> getNaturalDropsToRemove(LivingEntity entity) {
        CustomMobsTypes type = this.getCustomMobType(entity);
        if (type.equals(CustomMobsTypes.NONE)) return new ArrayList<Material>();
        return type.getCustomMob().getNaturalDropsToRemove();
    }

    public boolean preventNaturalDrops(LivingEntity entity) {
        CustomMobsTypes type = this.getCustomMobType(entity);
        if (type.equals(CustomMobsTypes.NONE)) return false;
        return type.getCustomMob().preventNaturalDrops();
    }

    private CustomMobsTypes getCustomMobType(LivingEntity entity) {
        if (entity.hasMetadata(CustomMob.CUSTOM_MOB_TYPE))
            return CustomMobsTypes.valueOf(entity.getMetadata(CustomMob.CUSTOM_MOB_TYPE).get(0).asString());
        return CustomMobsTypes.NONE;
    }

    private void loadOverworldReplacements() {
        this.overworldReplacementsMap = new HashMap<Integer, HashMap<EntityType, MobReplacement[]>>();
        // Difficulty zone 1 son los mobs clasicos de minecraft

        // Difficulty zone 2
        HashMap<EntityType, MobReplacement[]> difficultyZone2 = new HashMap<EntityType, MobReplacement[]>();
        difficultyZone2.put(EntityType.ZOMBIE, new MobReplacement[]{
                new MobReplacement(7, CustomMobsTypes.NONE),
                new MobReplacement(1, CustomMobsTypes.REVENANT),
                new MobReplacement(1, CustomMobsTypes.JOCKEY),
                new MobReplacement(1, CustomMobsTypes.RAGER),
        });
        difficultyZone2.put(EntityType.SKELETON, new MobReplacement[]{
                new MobReplacement(8, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.SKELETON_WIZARD),
        });
        difficultyZone2.put(EntityType.SPIDER, new MobReplacement[]{
                new MobReplacement(9, CustomMobsTypes.NONE),
                new MobReplacement(1, CustomMobsTypes.MIMIC)
        });
        difficultyZone2.put(EntityType.PHANTOM, new MobReplacement[]{
                new MobReplacement(8, CustomMobsTypes.NONE),
                new MobReplacement(1, CustomMobsTypes.BIG_PHANTOM),
                new MobReplacement(1, CustomMobsTypes.PHANTOM_RIDER),
        });
        difficultyZone2.put(EntityType.DROWNED, new MobReplacement[]{
                new MobReplacement(8, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.DROWNED_PIRATE),
        });
        difficultyZone2.put(EntityType.CREEPER, new MobReplacement[]{
                new MobReplacement(9, CustomMobsTypes.NONE),
                new MobReplacement(1, CustomMobsTypes.JUMPING_CREEPER),
        });
        this.overworldReplacementsMap.put(2, difficultyZone2);

        // Difficulty zone 3
        HashMap<EntityType, MobReplacement[]> difficultyZone3 = new HashMap<EntityType, MobReplacement[]>();
        difficultyZone3.put(EntityType.ZOMBIE, new MobReplacement[]{
                new MobReplacement(3, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.REVENANT),
                new MobReplacement(1, CustomMobsTypes.JOCKEY),
                new MobReplacement(1, CustomMobsTypes.RAGER),
                new MobReplacement(2, CustomMobsTypes.ORC),
                new MobReplacement(1, CustomMobsTypes.ORC_BRUTE),
        });
        difficultyZone3.put(EntityType.SKELETON, new MobReplacement[]{
                new MobReplacement(5, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.SKELETON_WIZARD),
                new MobReplacement(2, CustomMobsTypes.ORC_SHAMAN),
                new MobReplacement(1, CustomMobsTypes.NECROMANCER),
        });
        difficultyZone3.put(EntityType.SPIDER, new MobReplacement[]{
                new MobReplacement(7, CustomMobsTypes.NONE),
                new MobReplacement(1, CustomMobsTypes.REALENTING_SPIDER),
                new MobReplacement(1, CustomMobsTypes.MIMIC),
                new MobReplacement(1, CustomMobsTypes.ENERGY_NODE)
        });
        difficultyZone3.put(EntityType.PHANTOM, new MobReplacement[]{
                new MobReplacement(6, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.BIG_PHANTOM),
                new MobReplacement(2, CustomMobsTypes.PHANTOM_RIDER),
        });
        difficultyZone3.put(EntityType.DROWNED, new MobReplacement[]{
                new MobReplacement(6, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.DROWNED_PIRATE),
                new MobReplacement(2, CustomMobsTypes.WATER_DRIAD),
        });
        difficultyZone3.put(EntityType.CREEPER, new MobReplacement[]{
                new MobReplacement(5, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.JUMPING_CREEPER),
                new MobReplacement(2, CustomMobsTypes.AMPLIFIED_CREEPER),
                new MobReplacement(1, CustomMobsTypes.INCAPACITATING_CREEPER),
        });
        difficultyZone3.put(EntityType.STRAY, new MobReplacement[]{
                new MobReplacement(8, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.ICE_SOUL),
        });
        this.overworldReplacementsMap.put(3, difficultyZone3);

        // Difficulty zone 4
        HashMap<EntityType, MobReplacement[]> difficultyZone4 = new HashMap<EntityType, MobReplacement[]>();
        difficultyZone4.put(EntityType.ZOMBIE, new MobReplacement[]{
                new MobReplacement(1, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.REVENANT),
                new MobReplacement(2, CustomMobsTypes.JOCKEY),
                new MobReplacement(3, CustomMobsTypes.ORC),
                new MobReplacement(2, CustomMobsTypes.ORC_BRUTE),
        });
        difficultyZone4.put(EntityType.SKELETON, new MobReplacement[]{
                new MobReplacement(3, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.SKELETON_WIZARD),
                new MobReplacement(2, CustomMobsTypes.ORC_SHAMAN),
                new MobReplacement(2, CustomMobsTypes.NECROMANCER),
                new MobReplacement(1, CustomMobsTypes.UNDEAD_WIZARD),
        });
        difficultyZone4.put(EntityType.SPIDER, new MobReplacement[]{
                new MobReplacement(5, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.REALENTING_SPIDER),
                new MobReplacement(2, CustomMobsTypes.ENERGY_NODE),
                new MobReplacement(1, CustomMobsTypes.FIRE_ELEMENTAL)
        });
        difficultyZone4.put(EntityType.PHANTOM, new MobReplacement[]{
                new MobReplacement(3, CustomMobsTypes.NONE),
                new MobReplacement(3, CustomMobsTypes.BIG_PHANTOM),
                new MobReplacement(3, CustomMobsTypes.PHANTOM_RIDER),
                new MobReplacement(1, CustomMobsTypes.KING_PHANTOM),
        });
        difficultyZone4.put(EntityType.DROWNED, new MobReplacement[]{
                new MobReplacement(4, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.DROWNED_PIRATE),
                new MobReplacement(2, CustomMobsTypes.WATER_DRIAD),
                new MobReplacement(2, CustomMobsTypes.DROWNED_WIZARD),
        });
        difficultyZone4.put(EntityType.CREEPER, new MobReplacement[]{
                new MobReplacement(2, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.JUMPING_CREEPER),
                new MobReplacement(2, CustomMobsTypes.AMPLIFIED_CREEPER),
                new MobReplacement(2, CustomMobsTypes.INCAPACITATING_CREEPER),
                new MobReplacement(2, CustomMobsTypes.ENDER_MAGE),
        });
        difficultyZone4.put(EntityType.ENDERMAN, new MobReplacement[]{
                new MobReplacement(9, CustomMobsTypes.NONE),
                new MobReplacement(1, CustomMobsTypes.ENDER_WARRIOR),
        });
        difficultyZone4.put(EntityType.STRAY, new MobReplacement[]{
                new MobReplacement(7, CustomMobsTypes.NONE),
                new MobReplacement(3, CustomMobsTypes.ICE_SOUL),
        });
        this.overworldReplacementsMap.put(4, difficultyZone4);

        // Difficulty zone 5
        HashMap<EntityType, MobReplacement[]> difficultyZone5 = new HashMap<EntityType, MobReplacement[]>();
        difficultyZone5.put(EntityType.ZOMBIE, new MobReplacement[]{
                new MobReplacement(1, CustomMobsTypes.OGRE),
                new MobReplacement(1, CustomMobsTypes.REVENANT),
                new MobReplacement(1, CustomMobsTypes.JOCKEY),
                new MobReplacement(2, CustomMobsTypes.ORC),
                new MobReplacement(3, CustomMobsTypes.ORC_BRUTE),
                new MobReplacement(2, CustomMobsTypes.ENDER_WARRIOR),
        });
        difficultyZone5.put(EntityType.SKELETON, new MobReplacement[]{
                new MobReplacement(1, CustomMobsTypes.NONE),
                new MobReplacement(1, CustomMobsTypes.SKELETON_WIZARD),
                new MobReplacement(2, CustomMobsTypes.ORC_SHAMAN),
                new MobReplacement(3, CustomMobsTypes.NECROMANCER),
                new MobReplacement(3, CustomMobsTypes.UNDEAD_WIZARD),
        });
        difficultyZone5.put(EntityType.SPIDER, new MobReplacement[]{
                new MobReplacement(1, CustomMobsTypes.NONE),
                new MobReplacement(3, CustomMobsTypes.REALENTING_SPIDER),
                new MobReplacement(2, CustomMobsTypes.ENERGY_NODE),
                new MobReplacement(2, CustomMobsTypes.FIRE_ELEMENTAL),
                new MobReplacement(2, CustomMobsTypes.ICE_SOUL)
        });
        difficultyZone5.put(EntityType.PHANTOM, new MobReplacement[]{
                new MobReplacement(1, CustomMobsTypes.NONE),
                new MobReplacement(3, CustomMobsTypes.BIG_PHANTOM),
                new MobReplacement(3, CustomMobsTypes.PHANTOM_RIDER),
                new MobReplacement(3, CustomMobsTypes.KING_PHANTOM),
        });
        difficultyZone5.put(EntityType.DROWNED, new MobReplacement[]{
                new MobReplacement(2, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.DROWNED_PIRATE),
                new MobReplacement(3, CustomMobsTypes.WATER_DRIAD),
                new MobReplacement(3, CustomMobsTypes.DROWNED_WIZARD),
        });
        difficultyZone5.put(EntityType.CREEPER, new MobReplacement[]{
                new MobReplacement(2, CustomMobsTypes.JUMPING_CREEPER),
                new MobReplacement(3, CustomMobsTypes.AMPLIFIED_CREEPER),
                new MobReplacement(2, CustomMobsTypes.INCAPACITATING_CREEPER),
                new MobReplacement(3, CustomMobsTypes.ENDER_MAGE),
        });
        difficultyZone5.put(EntityType.ENDERMAN, new MobReplacement[]{
                new MobReplacement(7, CustomMobsTypes.NONE),
                new MobReplacement(1, CustomMobsTypes.ENDER_MAGE),
                new MobReplacement(2, CustomMobsTypes.ENDER_WARRIOR),
        });
        difficultyZone5.put(EntityType.STRAY, new MobReplacement[]{
                new MobReplacement(6, CustomMobsTypes.NONE),
                new MobReplacement(4, CustomMobsTypes.ICE_SOUL),
        });
        this.overworldReplacementsMap.put(5, difficultyZone5);
    }

    private void loadNetherReplacements() {
        this.netherReplacementsMap = new HashMap<EntityType, MobReplacement[]>();
        this.netherReplacementsMap.put(EntityType.ZOMBIFIED_PIGLIN, new MobReplacement[]{
                new MobReplacement(7, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.POSSESED_PIGLIN),
                new MobReplacement(1, CustomMobsTypes.FIRE_ELEMENTAL),
        });
        this.netherReplacementsMap.put(EntityType.SKELETON, new MobReplacement[]{
                new MobReplacement(5, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.SKELETON_WIZARD),
                new MobReplacement(2, CustomMobsTypes.NECROMANCER),
                new MobReplacement(1, CustomMobsTypes.UNDEAD_WIZARD),
        });
        this.netherReplacementsMap.put(EntityType.WITHER_SKELETON, new MobReplacement[]{
                new MobReplacement(8, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.NECROMANCER),
        });
        this.netherReplacementsMap.put(EntityType.GHAST, new MobReplacement[]{
                new MobReplacement(7, CustomMobsTypes.NONE),
                new MobReplacement(3, CustomMobsTypes.POWERED_GHAST),
        });
        this.netherReplacementsMap.put(EntityType.ENDERMAN, new MobReplacement[]{
                new MobReplacement(6, CustomMobsTypes.NONE),
                new MobReplacement(3, CustomMobsTypes.ENDER_MAGE),
                new MobReplacement(1, CustomMobsTypes.ENDER_WARRIOR),
        });
    }

    private void loadEndReplacements() {
        this.endReplacementsMap = new HashMap<EntityType, MobReplacement[]>();
        this.endReplacementsMap.put(EntityType.ENDERMAN, new MobReplacement[]{
                new MobReplacement(7, CustomMobsTypes.NONE),
                new MobReplacement(2, CustomMobsTypes.ENDER_MAGE),
                new MobReplacement(1, CustomMobsTypes.ENDER_WARRIOR),
        });
    }

    private MobReplacement getOverworldReplacement(LivingEntity mob) {
        int difficultyZone = DifficultyZone.calculateDifficultyZone(mob.getLocation());
        if (!this.overworldReplacementsMap.containsKey(difficultyZone) ||
                !this.overworldReplacementsMap.get(difficultyZone).containsKey(mob.getType())) return null;
        return this.pickReplacement(this.overworldReplacementsMap.get(difficultyZone).get(mob.getType()));
    }

    private MobReplacement getNetherReplacement(LivingEntity mob) {
        if (!this.netherReplacementsMap.containsKey(mob.getType())) return null;
        return this.pickReplacement(this.netherReplacementsMap.get(mob.getType()));
    }

    private MobReplacement getEndReplacement(LivingEntity mob) {
        if (!this.endReplacementsMap.containsKey(mob.getType())) return null;
        return this.pickReplacement(this.endReplacementsMap.get(mob.getType()));
    }

    private MobReplacement pickReplacement(MobReplacement[] replacements) {
        ArrayList<Integer> weightedIndexes = new ArrayList<Integer>();
        for (int i = 0; i < replacements.length; i++) {
            for (int j = 0; j < replacements[i].getSpawnWeight(); j++) {
                weightedIndexes.add(i);
            }
        }
        return replacements[weightedIndexes.get(rand.nextInt(weightedIndexes.size()))];
    }
}
