package com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.PurifiedWaterBottle;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ReinforcedLeather;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ReinforcedString;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.bags.Bag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.bags.OgreSkinBag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.bags.ReinforcedBag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.ManaBattery;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.DistanceTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.PowerTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.SpeedupTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.AdvancedManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.ManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.RegularManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.SupremeManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.spells.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.spells.SpellScroll;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.Rock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.daggers.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.greatsword.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows.BambooBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows.CompoundBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows.ReinforcedCompoundBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows.ReinforcedWoodenBow;
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
        for (SpellTypes spellType : SpellTypes.values()) {
            if (spellType.equals(SpellTypes.NONE)) continue;
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
        // Bloques
        this.items.add(new ManaGenerator());
        this.items.add(new TeleportingStone());
        this.items.add(new ManaTurret());
        this.items.add(new ManaStorager());
        this.items.add(new ManaRepeater());
        this.items.add(new ManaBottler());
        this.items.add(new ManaCharger());
        // Mana
        this.items.add(new ManaBattery());
        // Mejoras para la torreta
        this.items.add(new DistanceTablet());
        this.items.add(new PowerTablet());
        this.items.add(new SpeedupTablet());
    }

    public void loadRecipes() {
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        this.loadVanillaItemCustomRecipes(plugin);
        for (CustomItem customItem : this.items) {
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
        ShapedRecipe chainmailHelmet = new ShapedRecipe(new NamespacedKey(plugin, "chainmail_helmet"),
                new ItemStack(Material.CHAINMAIL_HELMET));
        chainmailHelmet.shape("ccc", "c_c", "___");
        chainmailHelmet.setIngredient('_', Material.AIR);
        chainmailHelmet.setIngredient('c', Material.CHAIN);
        plugin.getServer().addRecipe(chainmailHelmet);
        // Pechera de cota de malla
        ShapedRecipe chainmailChestplate = new ShapedRecipe(new NamespacedKey(plugin, "chainmail_chestplate"),
                new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        chainmailChestplate.shape("c_c", "ccc", "ccc");
        chainmailChestplate.setIngredient('_', Material.AIR);
        chainmailChestplate.setIngredient('c', Material.CHAIN);
        plugin.getServer().addRecipe(chainmailChestplate);
        // Pantalones de cota de malla
        ShapedRecipe chainmailLeggings = new ShapedRecipe(new NamespacedKey(plugin, "chainmail_leggings"),
                new ItemStack(Material.CHAINMAIL_LEGGINGS));
        chainmailLeggings.shape("ccc", "c_c", "c_c");
        chainmailLeggings.setIngredient('_', Material.AIR);
        chainmailLeggings.setIngredient('c', Material.CHAIN);
        plugin.getServer().addRecipe(chainmailLeggings);
        // Botas de cota de malla
        ShapedRecipe chainmailBoots = new ShapedRecipe(new NamespacedKey(plugin, "chainmail_boots"),
                new ItemStack(Material.CHAINMAIL_BOOTS));
        chainmailBoots.shape("___", "c_c", "c_c");
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
        ShapedRecipe enchantedGoldenApple = new ShapedRecipe(new NamespacedKey(plugin, "enchanted_golden_apple"),
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        enchantedGoldenApple.shape("ggg", "gag", "ggg");
        enchantedGoldenApple.setIngredient('g', Material.GOLD_BLOCK);
        enchantedGoldenApple.setIngredient('a', Material.APPLE);
        plugin.getServer().addRecipe(enchantedGoldenApple);
    }
}

