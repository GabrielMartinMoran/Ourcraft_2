package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.villagers;

import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class VillagerTrade {

    private Villager.Profession profession;
    private int level;
    private ItemStack firstCost;
    private ItemStack secondCost;
    private ItemStack result;

    public VillagerTrade(Villager.Profession profession, int level, ItemStack firstCost, ItemStack secondCost, ItemStack result) {
        this.profession = profession;
        this.level = level;
        this.firstCost = firstCost;
        this.secondCost = secondCost;
        this.result = result;
    }

    public MerchantRecipe getRecipe() {
        MerchantRecipe recipe = new MerchantRecipe(result, result.getAmount());
        recipe.addIngredient(firstCost);
        if (secondCost != null) recipe.addIngredient(secondCost);
        return recipe;
    }

    public Villager.Profession getProfession() {
        return this.profession;
    }

    public int getLevel() {
        return this.level;
    }
}
