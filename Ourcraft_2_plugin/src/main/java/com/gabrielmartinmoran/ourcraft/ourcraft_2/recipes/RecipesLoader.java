package com.gabrielmartinmoran.ourcraft.ourcraft_2.recipes;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class RecipesLoader {

    private ArrayList<CustomItem> items;

    private final int MAX_SPELLS_LEVEL = 5;

    public RecipesLoader() {
        this.items = new ArrayList<CustomItem>();
        this.items.add(new SpellBook(SpellTypes.NONE, 0));
        for (int i = 1; i <= MAX_SPELLS_LEVEL; i++) {
            this.items.add(new SpellBook(SpellTypes.LIGHTNING, i));
            this.items.add(new SpellBook(SpellTypes.NECROMANCER, i));
            this.items.add(new SpellBook(SpellTypes.FIREBALL, i));
            this.items.add(new SpellBook(SpellTypes.HEALING, i));
            this.items.add(new SpellBook(SpellTypes.LEVITATE, i));
            this.items.add(new SpellBook(SpellTypes.TELEPORT, i));
            this.items.add(new SpellBook(SpellTypes.SLOW_FALL, i));
            this.items.add(new SpellBook(SpellTypes.FIRE_RESISTANCE, i));
            this.items.add(new SpellBook(SpellTypes.MAGIC_ARROWS, i));
            this.items.add(new SpellBook(SpellTypes.MAGIC_MISSILES, i));
            this.items.add(new SpellBook(SpellTypes.POISON_CLOUD, i));
        }
    }

    public void loadRecipes() {
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        for (CustomItem customItem: this.items) {
            plugin.getServer().addRecipe(customItem.getRecipe());
        }
    }
}
