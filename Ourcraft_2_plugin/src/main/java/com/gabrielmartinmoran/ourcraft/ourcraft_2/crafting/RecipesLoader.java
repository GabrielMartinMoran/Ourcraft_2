package com.gabrielmartinmoran.ourcraft.ourcraft_2.recipes;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.bags.Bag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.bags.ReinforcedBag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellScroll;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers.WoodenDagger;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.greatsword.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;

public class RecipesLoader {

    private ArrayList<CustomItem> items;

    private final int MAX_SPELLS_LEVEL = 5;

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

        /*
        // Iterar recetas
        Iterator<Recipe> iter = Bukkit.getServer().recipeIterator();
        while (iter.hasNext()) {
            Recipe r = iter.next();
            // May not be safe to depend on == here for recipe comparison
            // Probably safer to compare the recipe result (an ItemStack)
            System.out.println(r.toString());
            if (r == unwantedRecipe) {
                iter.remove();
            }
        }*/
    }

    public void loadRecipes() {
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        for (CustomItem customItem: this.items) {
            plugin.getServer().addRecipe(customItem.getRecipe());
        }
    }
}

