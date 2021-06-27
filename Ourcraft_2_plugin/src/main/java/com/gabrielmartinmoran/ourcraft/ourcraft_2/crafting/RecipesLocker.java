package com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellScroll;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.villagers.VillagerTrade;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttribute;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.RecipesUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.naming.Name;
import javax.swing.plaf.basic.BasicButtonUI;
import java.util.*;
import java.util.stream.Collectors;

public class RecipesLocker {

    private static HashMap<String, ItemStack> recipesMap = new HashMap<String, ItemStack>();
    private static ArrayList<LockedRecipe> lockedRecipes = null;
    {
        if(lockedRecipes == null) initializeLockedRecipes();
    }

    public boolean isRecipeAvaliable(PlayerData playerData, Recipe recipe) {
        String recipeName = RecipesUtils.getRecipeName(recipe);
        // Prevenimos esto para que no pueda reparar items desde la mesa de crafteo
        if(recipeName.equals("minecraft:repair_item")) return false;
        boolean unlockedRecipe = true;
        for (LockedRecipe lockedRecipe : lockedRecipes) {
            if (lockedRecipe.compareName(recipeName)) {
                unlockedRecipe &= false;
                // Lo hacemos asi porque distintos atributos pueden permitir los mismos desbloqueos
                if (playerData.getAttributeLevel(lockedRecipe.getAttribute()) >= lockedRecipe.getMinLevel())
                    return true;
            }
        }
        return unlockedRecipe;
    }

    public PlayerAttributes getRecipeNeededAttribute(Recipe recipe) {
        String recipeName = RecipesUtils.getRecipeName(recipe);
        for (LockedRecipe lockedRecipe : lockedRecipes) {
            if (lockedRecipe.compareName(recipeName)) return lockedRecipe.getAttribute();
        }
        return null;
    }

    public static void notifyRecipesUnlocked(String playerName, PlayerAttributes attribute, int level) {
        List<LockedRecipe> filtered = lockedRecipes.stream().filter(recipe -> recipe.getAttribute().equals(attribute) && recipe.getMinLevel() == level).collect(Collectors.toList());
        boolean netherPortalUnlocked = attribute.equals(PlayerAttributes.MAGIC) && level == Config.NETHER_PORTAL_ACTIVATION_MAGIC_MIN_LEVEL;
        boolean teleporterUnlocked = attribute.equals(PlayerAttributes.MAGIC) && level == Config.TELEPORTER_CREATION_MAGIC_MIN_LEVEL;
        if(filtered.size() == 0 && !netherPortalUnlocked && !teleporterUnlocked) return;
        Player player = Bukkit.getServer().getPlayer(playerName);
        player.sendMessage("" + ChatColor.GREEN + "Has desbloqueado los siguientes crafteos:");
        for (LockedRecipe lockedRecipe : filtered) {
            ItemStack result = recipesMap.get(lockedRecipe.getRecipeName());
            String name = "";
            if (result.getItemMeta().getDisplayName() == null || result.getItemMeta().getDisplayName().length() == 0) {
                name = result.getType().name().replace("_", " ");
            } else {
                name = result.getItemMeta().getDisplayName();
            }
            String formattedName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            player.sendMessage(" - " + ChatColor.GOLD + formattedName);
        }
        if(netherPortalUnlocked) player.sendMessage(" - " + ChatColor.GOLD + "Portal al Nether");
        if(teleporterUnlocked) player.sendMessage(" - " + ChatColor.GOLD + "Teletransportador");
    }

    public static void registerServerRecipes() {
        for(Iterator<Recipe> iterator = Bukkit.recipeIterator(); iterator.hasNext();) {
            Recipe recipe = iterator.next();
            String recipeName = RecipesUtils.getRecipeName(recipe);
            recipesMap.put(recipeName, recipe.getResult());
        }
    }

    public static void registerRecipe(Recipe recipe) {
        String recipeName = RecipesUtils.getRecipeName(recipe);
        recipesMap.put(recipeName, recipe.getResult());
    }

    public boolean canActivateNetherPortal(Player player) {
        return PlayerDataProvider.get(player).getAttributeLevel(PlayerAttributes.MAGIC) >= Config.NETHER_PORTAL_ACTIVATION_MAGIC_MIN_LEVEL;
    }

    public boolean canCreateTeleporter(Player player) {
        return PlayerDataProvider.get(player).getAttributeLevel(PlayerAttributes.MAGIC) >= Config.TELEPORTER_CREATION_MAGIC_MIN_LEVEL;
    }

    public static void initializeLockedRecipes() {
        lockedRecipes = new ArrayList<LockedRecipe>();
        // MAGIC
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 5, "ourcraft_2:mana_potion"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 10, "minecraft:spectral_arrow"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 12, "minecraft:golden_apple"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 17, "minecraft:ender_eye"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 20, "minecraft:brewing_stand"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 21, "ourcraft_2:regular_mana_potion"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 23, "minecraft:ender_chest"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 25, "minecraft:respawn_anchor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 27, "minecraft:lectern"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 28, "minecraft:end_crystal"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 31, "minecraft:enchanting_table"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 35, "minecraft:lodestone"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 36, "ourcraft_2:enchanted_golden_apple"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 38, "ourcraft_2:advanced_mana_potion"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 40, "minecraft:conduit"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 42, "minecraft:beacon"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, 48, "ourcraft_2:supreme_mana_potion"));
        // Spellbooks y SpellScrolls
        for (SpellTypes spellType : SpellTypes.values()) {
            if (spellType.equals(SpellTypes.NONE)) continue;
            for (int i = 1; i <= RecipesLoader.MAX_SPELLS_LEVEL; i++) {
                int sbUnlockLevel = (i - 1) * 15; // Niveles 0 15 30 45 60
                int ssUnlockLevel = sbUnlockLevel + 7; // Niveles 7 22 37 52 67

                SpellBook sb = new SpellBook(spellType, i);
                lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, sbUnlockLevel, RecipesUtils.getRecipeName(sb.getRecipe())));

                SpellScroll ss = new SpellScroll(spellType, i);
                lockedRecipes.add(new LockedRecipe(PlayerAttributes.MAGIC, ssUnlockLevel, RecipesUtils.getRecipeName(ss.getRecipe())));
            }
        }

        // MELEE
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 5, "ourcraft_2:wooden_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 7, "minecraft:wooden_sword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 9, "ourcraft_2:wooden_axe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 11, "ourcraft_2:wooden_greatsword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 13, "ourcraft_2:golden_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 15, "minecraft:golden_sword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 17, "ourcraft_2:golden_axe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 19, "ourcraft_2:golden_greatsword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 21, "ourcraft_2:stone_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 23, "minecraft:stone_sword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 25, "ourcraft_2:stone_axe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 27, "ourcraft_2:stone_greatsword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 29, "minecraft:shield"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 31, "ourcraft_2:iron_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 33, "minecraft:anvil"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 35, "minecraft:iron_sword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 37, "ourcraft_2:iron_axe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 39, "ourcraft_2:iron_greatsword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 41, "minecraft:grindstone"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 43, "ourcraft_2:diamond_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 45, "minecraft:diamond_sword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 47, "ourcraft_2:diamond_axe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 49, "ourcraft_2:diamond_greatsword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 51, "ourcraft_2:netherite_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 53, "minecraft:netherite_sword_smithing"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 55, "ourcraft_2:netherite_axe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MELEE, 57, "ourcraft_2:netherite_greatsword"));

        // RANGED
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 5, "ourcraft_2:wooden_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 8, "minecraft:arrow"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 11, "ourcraft_2:bamboo_bow"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 14, "ourcraft_2:golden_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 17, "minecraft:target"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 20, "minecraft:bow"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 23, "ourcraft_2:stone_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 26, "minecraft:crossbow"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 29, "minecraft:fletching_table"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 32, "ourcraft_2:iron_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 35, "ourcraft_2:reinforced_wooden_bow"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 38, "ourcraft_2:diamond_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 41, "ourcraft_2:compound_bow"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 44, "ourcraft_2:netherite_dagger"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RANGED, 47, "ourcraft_2:reinforced_compound_bow"));

        // RESISTANCE
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 6, "minecraft:leather_boots"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 8, "minecraft:leather_helmet"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 10, "minecraft:leather_leggings"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 12, "minecraft:leather_chestplate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 14, "minecraft:golden_boots"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 16, "minecraft:golden_helmet"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 18, "minecraft:golden_leggings"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 20, "minecraft:golden_chestplate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 22, "minecraft:turtle_helmet"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 24, "minecraft:shield"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 26, "ourcraft_2:chainmail_boots"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 28, "ourcraft_2:chainmail_helmet"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 30, "ourcraft_2:chainmail_leggings"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 32, "ourcraft_2:chainmail_chestplate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 34, "minecraft:iron_boots"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 36, "minecraft:iron_helmet"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 38, "minecraft:iron_leggings"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 40, "minecraft:iron_chestplate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 42, "minecraft:anvil"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 44, "minecraft:diamond_boots"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 46, "minecraft:diamond_helmet"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 48, "minecraft:diamond_leggings"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 50, "minecraft:diamond_chestplate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 52, "minecraft:netherite_boots_smithing"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 54, "minecraft:netherite_helmet_smithing"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 58, "minecraft:netherite_leggings_smithing"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.RESISTANCE, 60, "minecraft:netherite_chestplate_smithing"));

        // CARPENTRY
        int CARPENTRY_PLANKS_UNLOCK_LEVEL = 2;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PLANKS_UNLOCK_LEVEL, "minecraft:oak_planks"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PLANKS_UNLOCK_LEVEL, "minecraft:spruce_planks"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PLANKS_UNLOCK_LEVEL, "minecraft:birch_planks"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PLANKS_UNLOCK_LEVEL, "minecraft:jungle_planks"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PLANKS_UNLOCK_LEVEL, "minecraft:acacia_planks"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PLANKS_UNLOCK_LEVEL, "minecraft:dark_oak_planks"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PLANKS_UNLOCK_LEVEL, "minecraft:crimson_planks"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PLANKS_UNLOCK_LEVEL, "minecraft:warped_planks"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 3, "minecraft:crafting_table"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 4, "minecraft:stick"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 4, "minecraft:stick_from_bamboo_item"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 5, "minecraft:wooden_axe"));
        int CARPENTRY_SIGN_UNLOCK_LEVEL = 6;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SIGN_UNLOCK_LEVEL, "minecraft:oak_sign"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SIGN_UNLOCK_LEVEL, "minecraft:spruce_sign"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SIGN_UNLOCK_LEVEL, "minecraft:birch_sign"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SIGN_UNLOCK_LEVEL, "minecraft:jungle_sign"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SIGN_UNLOCK_LEVEL, "minecraft:acacia_sign"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SIGN_UNLOCK_LEVEL, "minecraft:dark_oak_sign"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SIGN_UNLOCK_LEVEL, "minecraft:crimson_sign"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SIGN_UNLOCK_LEVEL, "minecraft:warped_sign"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 5, "minecraft:wooden_axe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 7, "minecraft:ladder"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 8, "minecraft:bowl"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 9, "minecraft:wooden_hoe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 10, "minecraft:wooden_shovel"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 11, "minecraft:wooden_pickaxe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 12, "minecraft:wooden_sword"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 13, "minecraft:fishing_rod"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 14, "minecraft:item_frame"));
        int CARPENTRY_BED_UNLOCK_LEVEL = 15;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:black_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:blue_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:brown_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:cyan_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:gray_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:green_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:light_blue_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:light_gray_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:lime_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:magenta_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:orange_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:pink_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:purple_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:red_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:white_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BED_UNLOCK_LEVEL, "minecraft:yellow_bed"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 17, "minecraft:chest"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 19, "minecraft:golden_axe"));
        int CARPENTRY_BUTTON_UNLOCK_LEVEL = 21;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BUTTON_UNLOCK_LEVEL, "minecraft:oak_button"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BUTTON_UNLOCK_LEVEL, "minecraft:spruce_button"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BUTTON_UNLOCK_LEVEL, "minecraft:birch_button"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BUTTON_UNLOCK_LEVEL, "minecraft:jungle_button"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BUTTON_UNLOCK_LEVEL, "minecraft:acacia_button"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BUTTON_UNLOCK_LEVEL, "minecraft:dark_oak_button"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BUTTON_UNLOCK_LEVEL, "minecraft:crimson_button"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BUTTON_UNLOCK_LEVEL, "minecraft:warped_button"));
        int CARPENTRY_SLAB_UNLOCK_LEVEL = 23;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SLAB_UNLOCK_LEVEL, "minecraft:oak_slab"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SLAB_UNLOCK_LEVEL, "minecraft:spruce_slab"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SLAB_UNLOCK_LEVEL, "minecraft:birch_slab"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SLAB_UNLOCK_LEVEL, "minecraft:jungle_slab"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SLAB_UNLOCK_LEVEL, "minecraft:acacia_slab"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SLAB_UNLOCK_LEVEL, "minecraft:dark_oak_slab"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SLAB_UNLOCK_LEVEL, "minecraft:crimson_slab"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_SLAB_UNLOCK_LEVEL, "minecraft:warped_slab"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 25, "minecraft:stone_axe"));
        int CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL = 27;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL, "minecraft:oak_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL, "minecraft:spruce_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL, "minecraft:birch_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL, "minecraft:jungle_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL, "minecraft:acacia_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL, "minecraft:dark_oak_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL, "minecraft:crimson_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_PRESSURE_PLATE_UNLOCK_LEVEL, "minecraft:warped_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 29, "minecraft:composter"));
        int CARPENTRY_STAIRS_UNLOCK_LEVEL = 31;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_STAIRS_UNLOCK_LEVEL, "minecraft:oak_stairs"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_STAIRS_UNLOCK_LEVEL, "minecraft:spruce_stairs"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_STAIRS_UNLOCK_LEVEL, "minecraft:birch_stairs"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_STAIRS_UNLOCK_LEVEL, "minecraft:jungle_stairs"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_STAIRS_UNLOCK_LEVEL, "minecraft:acacia_stairs"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_STAIRS_UNLOCK_LEVEL, "minecraft:dark_oak_stairs"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_STAIRS_UNLOCK_LEVEL, "minecraft:crimson_stairs"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_STAIRS_UNLOCK_LEVEL, "minecraft:warped_stairs"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 33, "minecraft:iron_axe"));
        int CARPENTRY_FENCE_UNLOCK_LEVEL = 35;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_UNLOCK_LEVEL, "minecraft:oak_fence"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_UNLOCK_LEVEL, "minecraft:spruce_fence"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_UNLOCK_LEVEL, "minecraft:birch_fence"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_UNLOCK_LEVEL, "minecraft:jungle_fence"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_UNLOCK_LEVEL, "minecraft:acacia_fence"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_UNLOCK_LEVEL, "minecraft:dark_oak_fence"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_UNLOCK_LEVEL, "minecraft:crimson_fence"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_UNLOCK_LEVEL, "minecraft:warped_fence"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 37, "minecraft:cartography_table"));
        int CARPENTRY_FENCE_GATE_UNLOCK_LEVEL = 39;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_GATE_UNLOCK_LEVEL, "minecraft:oak_fence_gate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_GATE_UNLOCK_LEVEL, "minecraft:spruce_fence_gate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_GATE_UNLOCK_LEVEL, "minecraft:birch_fence_gate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_GATE_UNLOCK_LEVEL, "minecraft:jungle_fence_gate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_GATE_UNLOCK_LEVEL, "minecraft:acacia_fence_gate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_GATE_UNLOCK_LEVEL, "minecraft:dark_oak_fence_gate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_GATE_UNLOCK_LEVEL, "minecraft:crimson_fence_gate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_FENCE_GATE_UNLOCK_LEVEL, "minecraft:warped_fence_gate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 41, "minecraft:loom"));
        int CARPENTRY_BOAT_UNLOCK_LEVEL = 43;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BOAT_UNLOCK_LEVEL, "minecraft:oak_boat"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BOAT_UNLOCK_LEVEL, "minecraft:spruce_boat"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BOAT_UNLOCK_LEVEL, "minecraft:birch_boat"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BOAT_UNLOCK_LEVEL, "minecraft:jungle_boat"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BOAT_UNLOCK_LEVEL, "minecraft:acacia_boat"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_BOAT_UNLOCK_LEVEL, "minecraft:dark_oak_boat"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 45, "minecraft:barrel"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 47, "minecraft:bow"));
        int CARPENTRY_TRAPDOOR_UNLOCK_LEVEL = 49;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_TRAPDOOR_UNLOCK_LEVEL, "minecraft:oak_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_TRAPDOOR_UNLOCK_LEVEL, "minecraft:spruce_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_TRAPDOOR_UNLOCK_LEVEL, "minecraft:birch_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_TRAPDOOR_UNLOCK_LEVEL, "minecraft:jungle_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_TRAPDOOR_UNLOCK_LEVEL, "minecraft:acacia_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_TRAPDOOR_UNLOCK_LEVEL, "minecraft:dark_oak_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_TRAPDOOR_UNLOCK_LEVEL, "minecraft:crimson_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_TRAPDOOR_UNLOCK_LEVEL, "minecraft:warped_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 51, "minecraft:beehive"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 53, "minecraft:bookshelf"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 55, "minecraft:scaffolding"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 56, "minecraft:jukebox"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 57, "minecraft:diamond_axe"));
        int CARPENTRY_DOOR_UNLOCK_LEVEL = 59;
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_DOOR_UNLOCK_LEVEL, "minecraft:oak_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_DOOR_UNLOCK_LEVEL, "minecraft:spruce_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_DOOR_UNLOCK_LEVEL, "minecraft:birch_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_DOOR_UNLOCK_LEVEL, "minecraft:jungle_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_DOOR_UNLOCK_LEVEL, "minecraft:acacia_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_DOOR_UNLOCK_LEVEL, "minecraft:dark_oak_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_DOOR_UNLOCK_LEVEL, "minecraft:crimson_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, CARPENTRY_DOOR_UNLOCK_LEVEL, "minecraft:warped_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.CARPENTRY, 60, "minecraft:netherite_axe_smithing"));

        // MINNING
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 5, "minecraft:wooden_pickaxe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 6, "minecraft:wooden_shovel"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 8, "minecraft:furnace"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 9, "minecraft:bucket"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 11, "minecraft:stone_pickaxe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 12, "minecraft:stone_shovel"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 15, "minecraft:tnt"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 17, "minecraft:golden_pickaxe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 18, "minecraft:golden_shovel"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 19, "minecraft:shears"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 20, "minecraft:chain"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 22, "minecraft:stonecutter"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 24, "minecraft:iron_pickaxe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 25, "minecraft:iron_shovel"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 26, "minecraft:compass"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 27, "minecraft:blast_furnace"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 28, "minecraft:clock"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 29, "minecraft:minecart"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 30, "minecraft:rail"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 31, "minecraft:powered_rail"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 32, "minecraft:detector_rail"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 33, "minecraft:activator_rail"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 35, "minecraft:hopper"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 37, "minecraft:light_weighted_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 39, "minecraft:heavy_weighted_pressure_plate"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 41, "minecraft:cauldron"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 43, "minecraft:iron_trapdoor"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 45, "minecraft:iron_door"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 47, "minecraft:iron_bars"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 49, "minecraft:smithing_table"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 51, "minecraft:daylight_detector"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 53, "minecraft:diamond_pickaxe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 54, "minecraft:diamond_shovel"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 56, "minecraft:anvil"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 59, "minecraft:netherite_pickaxe_smithing"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.MINING, 60, "minecraft:netherite_shovel_smithing"));

        // FARMING
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 5, "minecraft:wooden_hoe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 7, "minecraft:bread"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 9, "minecraft:golden_hoe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 11, "minecraft:mushroom_stew"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 13, "minecraft:beetroot_soup"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 15, "minecraft:hay_block"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 17, "minecraft:stone_hoe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 19, "minecraft:composter"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 21, "minecraft:sugar_from_sugar_cane"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 23, "minecraft:sugar_from_honey_bottle"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 25, "minecraft:cookie"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 27, "minecraft:rabbit_stew_from_brown_mushroom"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 29, "minecraft:rabbit_stew_from_red_mushroom"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 31, "minecraft:white_wool_from_string"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 33, "minecraft:flower_pot"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 35, "minecraft:smoker"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 37, "minecraft:cake"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 39, "minecraft:golden_carrot"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 41, "minecraft:glistering_melon_slice"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 43, "minecraft:iron_hoe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 45, "minecraft:honey_block"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 47, "minecraft:honeycomb_block"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 49, "minecraft:loom"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 51, "minecraft:lead"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 53, "minecraft:diamond_hoe"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 55, "minecraft:beehive"));
        lockedRecipes.add(new LockedRecipe(PlayerAttributes.FARMING, 57, "minecraft:netherite_hoe_smithing"));
    }
}
