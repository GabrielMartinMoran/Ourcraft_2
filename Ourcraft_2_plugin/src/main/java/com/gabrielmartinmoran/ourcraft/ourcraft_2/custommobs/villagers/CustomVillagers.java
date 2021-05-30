package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.villagers.VillagerTrade;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CustomVillagers {

    private Random rand;
    private List<VillagerTrade> trades = Arrays.asList(
        new VillagerTrade(Villager.Profession.FARMER, 1, getCoin(1), null, new ItemStack(Material.WHEAT_SEEDS, 32)),
        new VillagerTrade(Villager.Profession.FARMER, 1, getCoin(2), null, new ItemStack(Material.CARROT, 32)),
        new VillagerTrade(Villager.Profession.FARMER, 1, getCoin(3), null, new ItemStack(Material.POTATO, 32)),
        new VillagerTrade(Villager.Profession.FARMER, 1, getCoin(3), null, new ItemStack(Material.BEETROOT_SEEDS, 64)),
        new VillagerTrade(Villager.Profession.FARMER, 1, getCoin(3), null, new ItemStack(Material.BREAD, 10)),
        new VillagerTrade(Villager.Profession.FARMER, 1, getCoin(3), null, new ItemStack(Material.APPLE, 5)),
        new VillagerTrade(Villager.Profession.FARMER, 1, getCoin(3), null, new ItemStack(Material.HAY_BLOCK, 3)),
        new VillagerTrade(Villager.Profession.FARMER, 1, new ItemStack(Material.WHEAT, 15), null, getCoin(1))
    );

    public CustomVillagers() {
        this.rand = new Random();
    }

    private ItemStack getCoin(int amount) {
        ItemStack coin = new ItemStack(Material.YELLOW_DYE, amount);
        ItemMeta meta = coin.getItemMeta();
        meta.setDisplayName("Moneda de oro");
        coin.setItemMeta(meta);
        return coin;
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

    private VillagerTrade getRecipe(Villager villager) {
        List<VillagerTrade> filteredTrades = this.trades.stream().filter(trade ->
                trade.getProfession().equals(villager.getProfession()) &&
                trade.getLevel() == villager.getVillagerLevel() &&
                !this.tradeAlreadyAdded(villager, trade)
        ).collect(Collectors.toList());
        if(filteredTrades.isEmpty()) return null;
        return filteredTrades.get(rand.nextInt(filteredTrades.size()));
    }
}
