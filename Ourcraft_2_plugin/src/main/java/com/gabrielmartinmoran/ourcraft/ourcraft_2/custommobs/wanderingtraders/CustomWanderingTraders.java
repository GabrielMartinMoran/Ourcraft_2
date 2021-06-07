package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.villagers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.CopperCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.GoldenCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.PlatinumCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.SilverCoin;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CustomWanderingTraders {

    private CopperCoin copperCoin = new CopperCoin();
    private SilverCoin silverCoin = new SilverCoin();
    private GoldenCoin goldenCoin = new GoldenCoin();
    private PlatinumCoin platinumCoin = new PlatinumCoin();
    private Random rand;
    private List<VillagerTrade> trades = Arrays.asList(
            new VillagerTrade(Villager.Profession.NONE, 1, copperCoin.getItem(1), null, new ItemStack(Material.WHEAT_SEEDS, 32)),
            new VillagerTrade(Villager.Profession.NONE, 1, copperCoin.getItem(2), null, new ItemStack(Material.CARROT, 32)),
            new VillagerTrade(Villager.Profession.NONE, 1, copperCoin.getItem(3), null, new ItemStack(Material.POTATO, 32)),
            new VillagerTrade(Villager.Profession.NONE, 1, copperCoin.getItem(3), null, new ItemStack(Material.BEETROOT_SEEDS, 64)),
            new VillagerTrade(Villager.Profession.NONE, 1, silverCoin.getItem(3), null, new ItemStack(Material.BREAD, 10)),
            new VillagerTrade(Villager.Profession.NONE, 1, goldenCoin.getItem(3), null, new ItemStack(Material.APPLE, 5)),
            new VillagerTrade(Villager.Profession.NONE, 1, platinumCoin.getItem(3), null, new ItemStack(Material.HAY_BLOCK, 3)),
            new VillagerTrade(Villager.Profession.NONE, 1, new ItemStack(Material.WHEAT, 15), null, copperCoin.getItem(1))
    );

    public CustomWanderingTraders() {
        this.rand = new Random();
    }

    // Retorna false cuando no encuentra recipe para agregar
    public boolean addRecipe(Villager villager, MerchantRecipe originalRecipe) {
        VillagerTrade trade = this.getRecipe(villager);
        if (trade == null) return false;
        ArrayList<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>(villager.getRecipes());
        MerchantRecipe recipe = trade.getRecipe();
        recipe.setMaxUses(originalRecipe.getMaxUses());
        recipe.setVillagerExperience(originalRecipe.getVillagerExperience());
        recipes.add(recipe);
        villager.setRecipes(recipes);
        return true;
    }

    private boolean tradeAlreadyAdded(WanderingTrader trader, VillagerTrade trade) {
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

    private VillagerTrade getRecipe(WanderingTrader trader) {
        List<VillagerTrade> filteredTrades = this.trades.stream().filter(trade -> !this.tradeAlreadyAdded(trader, trade)).collect(Collectors.toList());
        if(filteredTrades.isEmpty()) return null;
        return filteredTrades.get(rand.nextInt(filteredTrades.size()));
    }
}
