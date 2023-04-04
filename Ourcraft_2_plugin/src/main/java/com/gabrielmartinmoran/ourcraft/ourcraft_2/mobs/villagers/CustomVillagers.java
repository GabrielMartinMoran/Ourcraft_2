package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.villagers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins.CopperCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins.GoldenCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins.PlatinumCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins.SilverCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.economy.EconomyTable;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class CustomVillagers {

    private CopperCoin copperCoin = new CopperCoin();
    private SilverCoin silverCoin = new SilverCoin();
    private GoldenCoin goldenCoin = new GoldenCoin();
    private PlatinumCoin platinumCoin = new PlatinumCoin();

    private SecureRandom rand;
    public CustomVillagers() {
        this.rand = new SecureRandom();
    }

    // Retorna false cuando no encuentra recipe para agregar
    public boolean addRecipe(Villager villager, MerchantRecipe originalRecipe) {
        MerchantRecipe recipe = EconomyTable.getTrade(villager.getProfession(), villager.getVillagerLevel());
        if (recipe == null) return false;
        ArrayList<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>(villager.getRecipes());
        recipe.setMaxUses(originalRecipe.getMaxUses());
        recipe.setVillagerExperience(originalRecipe.getVillagerExperience());
        recipes.add(recipe);
        villager.setRecipes(recipes);
        return true;
    }

    private boolean tradeAlreadyAdded(Villager villager, VillagerTrade trade) {
        MerchantRecipe newRecipe = trade.getRecipe();
        for (MerchantRecipe recipe: villager.getRecipes()) {
            List<ItemStack> existingIngredients = recipe.getIngredients();
            List<ItemStack> newIngredients = newRecipe.getIngredients();
            boolean sameFirstCost = existingIngredients.get(0).isSimilar(newIngredients.get(0)) &&
                    existingIngredients.get(0).getAmount() == newIngredients.get(0).getAmount();
            boolean sameSecondCost = (existingIngredients.size() == 1 && newIngredients.size() == 1) ||
                    ((existingIngredients.size() > 1 && newIngredients.size() > 1) && existingIngredients.get(1).isSimilar(newIngredients.get(1)) &&
                    existingIngredients.get(1).getAmount() == newIngredients.get(1).getAmount());
            boolean sameResult = recipe.getResult().isSimilar(newRecipe.getResult()) && recipe.getResult().getAmount() == newRecipe.getResult().getAmount();
            if(sameFirstCost && sameSecondCost && sameResult) return true;
        }
        return false;
    }

    /*private VillagerTrade getRecipe(Villager villager) {
        List<VillagerTrade> filteredTrades = this.trades.stream().filter(trade ->
                trade.getProfession().equals(villager.getProfession()) &&
                trade.getLevel() == villager.getVillagerLevel() &&
                !this.tradeAlreadyAdded(villager, trade)
        ).collect(Collectors.toList());
        if(filteredTrades.isEmpty()) return null;
        return filteredTrades.get(rand.nextInt(filteredTrades.size()));
    }*/
}
