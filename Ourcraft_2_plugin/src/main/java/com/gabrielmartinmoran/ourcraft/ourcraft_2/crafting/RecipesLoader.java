package com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.bags.Bag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.bags.OgreSkinBag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.bags.ReinforcedBag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores.RawGold;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores.RawGoldBlock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores.RawIron;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores.RawIronBlock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.AdvancedManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.ManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.RegularManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.SupremeManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellScroll;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.Rock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.greatsword.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged.bows.BambooBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged.bows.CompoundBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged.bows.ReinforcedCompoundBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged.bows.ReinforcedWoodenBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class RecipesLoader {

    private ArrayList<CustomItem> items;
    private static ArrayList<CustomItem> smithingRecipes = new ArrayList<CustomItem>();

    public final static int MAX_SPELLS_LEVEL = 5;

    public RecipesLoader() {
        this.items = new ArrayList<CustomItem>();
        this.items.add(new SpellBook(SpellTypes.NONE, 0));
        // Spellbooks y SpellScrolls
        for (SpellTypes spellType: SpellTypes.values()) {
            if(spellType.equals(SpellTypes.NONE)) continue;
            for (int i = 1; i <= MAX_SPELLS_LEVEL; i++) {
                this.items.add(new SpellBook(spellType, i));
                this.items.add(new SpellScroll(spellType, i));
            }
        }
        this.items.add(new Bag());
        this.items.add(new ReinforcedBag());
        this.items.add(new ReinforcedLeather());
        this.items.add(new ReinforcedString());
        this.items.add(new OgreSkinBag());
        // Dagas
        this.items.add(new WoodenDagger());
        this.items.add(new GoldenDagger());
        this.items.add(new StoneDagger());
        this.items.add(new IronDagger());
        this.items.add(new DiamondDagger());
        this.items.add(new NetheriteDagger());
        // Espadones
        this.items.add(new WoodenGreatSword());
        this.items.add(new GoldenGreatSword());
        this.items.add(new StoneGreatSword());
        this.items.add(new IronGreatSword());
        this.items.add(new DiamondGreatSword());
        this.items.add(new NetheriteGreatSword());
        // Roca
        this.items.add(new Rock());
        // Arcos
        this.items.add(new BambooBow());
        this.items.add(new ReinforcedWoodenBow());
        this.items.add(new CompoundBow());
        this.items.add(new ReinforcedCompoundBow());
        // Pociones de mana
        this.items.add(new ManaPotion());
        this.items.add(new RegularManaPotion());
        this.items.add(new AdvancedManaPotion());
        this.items.add(new SupremeManaPotion());
        // Agua purificada
        this.items.add(new PurifiedWaterBottle());
        // Ores
        this.items.add(new RawIron());
        this.items.add(new RawIronBlock());
        this.items.add(new RawGold());
        this.items.add(new RawGoldBlock());
    }

    public void loadRecipes() {
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        this.loadVanillaItemCustomRecipes(plugin);
        for (CustomItem customItem: this.items) {
            if (customItem.getRecipe() instanceof SmithingRecipe) {
                smithingRecipes.add(customItem);
                RecipesLocker.registerRecipe(customItem.getRecipe());
            } else {
                plugin.getServer().addRecipe(customItem.getRecipe());
            }
        }
        // Las de smithing custom no se cargan aca
        RecipesLocker.registerServerRecipes();
    }

    public static ArrayList<CustomItem> getSmithingRecipes() {
        return smithingRecipes;
    }

    private void loadVanillaItemCustomRecipes(JavaPlugin plugin) {
        // Casco de cota de malla
        ShapedRecipe chainmailHelmet = new ShapedRecipe(new NamespacedKey(plugin,"chainmail_helmet"), new ItemStack(Material.CHAINMAIL_HELMET));
        chainmailHelmet.shape("ccc","c_c","___");
        chainmailHelmet.setIngredient('_', Material.AIR);
        chainmailHelmet.setIngredient('c', Material.CHAIN);
        plugin.getServer().addRecipe(chainmailHelmet);
        // Pechera de cota de malla
        ShapedRecipe chainmailChestplate = new ShapedRecipe(new NamespacedKey(plugin,"chainmail_chestplate"), new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        chainmailChestplate.shape("c_c","ccc","ccc");
        chainmailChestplate.setIngredient('_', Material.AIR);
        chainmailChestplate.setIngredient('c', Material.CHAIN);
        plugin.getServer().addRecipe(chainmailChestplate);
        // Pantalones de cota de malla
        ShapedRecipe chainmailLeggings = new ShapedRecipe(new NamespacedKey(plugin,"chainmail_leggings"), new ItemStack(Material.CHAINMAIL_LEGGINGS));
        chainmailLeggings.shape("ccc","c_c","c_c");
        chainmailLeggings.setIngredient('_', Material.AIR);
        chainmailLeggings.setIngredient('c', Material.CHAIN);
        plugin.getServer().addRecipe(chainmailLeggings);
        // Botas de cota de malla
        ShapedRecipe chainmailBoots = new ShapedRecipe(new NamespacedKey(plugin,"chainmail_boots"), new ItemStack(Material.CHAINMAIL_BOOTS));
        chainmailBoots.shape("___","c_c","c_c");
        chainmailBoots.setIngredient('_', Material.AIR);
        chainmailBoots.setIngredient('c', Material.CHAIN);
        plugin.getServer().addRecipe(chainmailBoots);
        // Carne de zombie a cuero
        plugin.getServer().addRecipe(new FurnaceRecipe(
                new NamespacedKey(plugin, "leather_from_rotten_flesh_cooking"),
                new ItemStack(Material.LEATHER),
                new RecipeChoice.MaterialChoice(Material.ROTTEN_FLESH),
                0.35f,
                350
        ));
        plugin.getServer().addRecipe(new CampfireRecipe(
                new NamespacedKey(plugin, "leather_from_rotten_flesh_campfire_cooking"),
                new ItemStack(Material.LEATHER),
                new RecipeChoice.MaterialChoice(Material.ROTTEN_FLESH),
                0.35f,
                700
        ));
        plugin.getServer().addRecipe(new SmokingRecipe(
                new NamespacedKey(plugin, "leather_from_rotten_flesh_smoking_cooking"),
                new ItemStack(Material.LEATHER),
                new RecipeChoice.MaterialChoice(Material.ROTTEN_FLESH),
                0.35f,
                200
        ));
        // Manzana de oro encantada
        ShapedRecipe enchantedGoldenApple = new ShapedRecipe(new NamespacedKey(plugin,"enchanted_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        enchantedGoldenApple.shape("ggg","gag","ggg");
        enchantedGoldenApple.setIngredient('g', Material.GOLD_BLOCK);
        enchantedGoldenApple.setIngredient('a', Material.APPLE);
        plugin.getServer().addRecipe(enchantedGoldenApple);
        // Lingote de hierro
        plugin.getServer().addRecipe(new FurnaceRecipe(
                new NamespacedKey(plugin, "iron_ingot_from_smelting_raw_iron"),
                new ItemStack(Material.IRON_INGOT),
                new RecipeChoice.ExactChoice(new RawIron().getItem()),
                0.7f,
                200
        ));
        plugin.getServer().addRecipe(new BlastingRecipe(
                new NamespacedKey(plugin, "iron_ingot_from_blasting_raw_iron"),
                new ItemStack(Material.IRON_INGOT),
                new RecipeChoice.ExactChoice(new RawIron().getItem()),
                0.7f,
                100
        ));
        // Bloque de hierro
        plugin.getServer().addRecipe(new FurnaceRecipe(
                new NamespacedKey(plugin, "iron_block_from_smelting_raw_iron_block"),
                new ItemStack(Material.IRON_BLOCK),
                new RecipeChoice.ExactChoice(new RawIronBlock().getItem()),
                6.3f,
                1800
        ));
        plugin.getServer().addRecipe(new BlastingRecipe(
                new NamespacedKey(plugin, "iron_block_from_blasting_raw_iron_block"),
                new ItemStack(Material.IRON_BLOCK),
                new RecipeChoice.ExactChoice(new RawIronBlock().getItem()),
                6.3f,
                900
        ));
        // Lingote de oro
        plugin.getServer().addRecipe(new FurnaceRecipe(
                new NamespacedKey(plugin, "gold_ingot_from_smelting_raw_gold"),
                new ItemStack(Material.GOLD_INGOT),
                new RecipeChoice.ExactChoice(new RawGold().getItem()),
                1f,
                200
        ));
        plugin.getServer().addRecipe(new BlastingRecipe(
                new NamespacedKey(plugin, "gold_ingot_from_blasting_raw_gold"),
                new ItemStack(Material.GOLD_INGOT),
                new RecipeChoice.ExactChoice(new RawGold().getItem()),
                1f,
                100
        ));
        // Bloque de oro
        plugin.getServer().addRecipe(new FurnaceRecipe(
                new NamespacedKey(plugin, "gold_block_from_smelting_raw_gold_block"),
                new ItemStack(Material.GOLD_BLOCK),
                new RecipeChoice.ExactChoice(new RawGoldBlock().getItem()),
                9f,
                1800
        ));
        plugin.getServer().addRecipe(new BlastingRecipe(
                new NamespacedKey(plugin, "gold_block_from_blasting_raw_gold_block"),
                new ItemStack(Material.GOLD_BLOCK),
                new RecipeChoice.ExactChoice(new RawGoldBlock().getItem()),
                9f,
                900
        ));
    }
}

