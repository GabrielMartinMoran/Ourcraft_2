package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.wanderingtraders;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class WanderingTraderTrade {

    private ItemStack firstCost;
    private ItemStack secondCost;
    private ItemStack result;
    private boolean unlimitedTrades;

    public WanderingTraderTrade(ItemStack firstCost, ItemStack secondCost, ItemStack result, boolean unlimitedTrades) {
        this.firstCost = firstCost;
        this.secondCost = secondCost;
        this.result = result;
        this.unlimitedTrades = unlimitedTrades;
    }

    public WanderingTraderTrade(ItemStack firstCost, ItemStack secondCost, ItemStack result) {
        this.firstCost = firstCost;
        this.secondCost = secondCost;
        this.result = result;
        this.unlimitedTrades = false;
    }

    public MerchantRecipe getRecipe() {
        MerchantRecipe recipe = new MerchantRecipe(result, result.getAmount());
        recipe.addIngredient(firstCost);
        if (secondCost != null) recipe.addIngredient(secondCost);
        return recipe;
    }

    public boolean isUnlimitedTrade() {
        return this.unlimitedTrades;
    }
}
