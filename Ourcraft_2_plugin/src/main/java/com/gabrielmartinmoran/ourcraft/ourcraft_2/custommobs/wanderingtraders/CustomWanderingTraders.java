package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.wanderingtraders;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.armors.CustomArmor;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.armors.CustomArmorGenerator;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.CopperCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.GoldenCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.PlatinumCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.SilverCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.CustomMeleeWeapon;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.CustomMeleeWeaponGenerator;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.villagers.VillagerTrade;
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
    private CustomArmorGenerator customArmorGenerator;
    private CustomMeleeWeaponGenerator customMeleeWeaponGenerator;
    private final int UNLIMITED_TRADES_AMOUNT = 999999;
    private final double CUSTOM_ARMOR_PROBABILITY = 0.3d;
    private final double CUSTOM_WEAPON_PROBABILITY = 0.3d;
    private final double SECOND_RECIPE_PROBABILITY = 0.5d;
    private final int CUSTOM_ARMOR_MAX_USES = 1;
    private List<WanderingTraderTrade> trades = Arrays.asList(
            new WanderingTraderTrade(copperCoin.getItem(10), null, silverCoin.getItem(1), true),
            new WanderingTraderTrade(silverCoin.getItem(10), null, goldenCoin.getItem(1), true),
            new WanderingTraderTrade(goldenCoin.getItem(10), null, platinumCoin.getItem(1), true),
            new WanderingTraderTrade(silverCoin.getItem(1), null, copperCoin.getItem(10), true),
            new WanderingTraderTrade(goldenCoin.getItem(1), null, silverCoin.getItem(10), true),
            new WanderingTraderTrade(platinumCoin.getItem(1), null, goldenCoin.getItem(10), true)
    );

    public CustomWanderingTraders() {
        this.rand = new Random();
        this.customArmorGenerator = new CustomArmorGenerator();
        this.customMeleeWeaponGenerator = new CustomMeleeWeaponGenerator();
    }

    // Retorna false cuando no encuentra recipe para agregar
    public boolean addRecipe(WanderingTrader trader, MerchantRecipe originalRecipe) {
        ArrayList<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>(trader.getRecipes());
        MerchantRecipe recipe = this.getRandomRecipe(trader, originalRecipe);
        if (recipe != null) recipes.add(recipe);
        // Probabilidad de que agregue una segunda receta
        if (this.rand.nextDouble() <= SECOND_RECIPE_PROBABILITY) {
            MerchantRecipe secondRecipe = this.getRandomRecipe(trader, originalRecipe);
            if (secondRecipe != null) recipes.add(secondRecipe);
        }
        if (recipes.isEmpty()) return false;
        trader.setRecipes(recipes);
        return true;
    }

    private MerchantRecipe getRandomRecipe(WanderingTrader trader, MerchantRecipe originalRecipe) {
        MerchantRecipe recipe = null;
        if(this.shouldAddCustomArmor()) {
            recipe = this.generateCustomArmorRecipe();
        } else if(this.shouldAddCustomWeapon()){
            recipe = this.generateCustomWeaponRecipe();
        } else {
            WanderingTraderTrade trade = this.getRecipe(trader);
            if (trade == null) return null;
            recipe = trade.getRecipe();
            if (trade.isUnlimitedTrade()) {
                recipe.setMaxUses(UNLIMITED_TRADES_AMOUNT);
            } else {
                recipe.setMaxUses(originalRecipe.getMaxUses());
            }
            recipe.setVillagerExperience(originalRecipe.getVillagerExperience());
        }
        return recipe;
    }

    private boolean shouldAddCustomArmor() {
        return this.rand.nextDouble() <= CUSTOM_ARMOR_PROBABILITY;
    }

    private boolean shouldAddCustomWeapon() {
        return this.rand.nextDouble() <= CUSTOM_WEAPON_PROBABILITY;
    }

    private boolean tradeAlreadyAdded(WanderingTrader trader, WanderingTraderTrade trade) {
        MerchantRecipe newRecipe = trade.getRecipe();
        for (MerchantRecipe recipe: trader.getRecipes()) {
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

    private WanderingTraderTrade getRecipe(WanderingTrader trader) {
        List<WanderingTraderTrade> filteredTrades = this.trades.stream().filter(trade -> !this.tradeAlreadyAdded(trader, trade)).collect(Collectors.toList());
        if(filteredTrades.isEmpty()) return null;
        return filteredTrades.get(rand.nextInt(filteredTrades.size()));
    }

    private MerchantRecipe generateCustomArmorRecipe() {
        CustomArmor customArmor = this.customArmorGenerator.generateArmor();
        MerchantRecipe recipe = new MerchantRecipe(customArmor.getItem(), CUSTOM_ARMOR_MAX_USES);
        long copperCost = customArmor.calculateCopperCost();
        this.addRecipeCoinsCost(recipe, copperCost);
        return recipe;
    }

    private MerchantRecipe generateCustomWeaponRecipe() {
        CustomMeleeWeapon customWeapon = this.customMeleeWeaponGenerator.generateWeapon();
        MerchantRecipe recipe = new MerchantRecipe(customWeapon.getItem(), CUSTOM_ARMOR_MAX_USES);
        long copperCost = customWeapon.calculateCopperCost();
        this.addRecipeCoinsCost(recipe, copperCost);
        return recipe;
    }

    private void addRecipeCoinsCost(MerchantRecipe recipe, long copperCost) {
        int copperCoins = 0;
        int silverCoins = 0;
        int goldenCoins = 0;
        int platinumCoins = 0;
        while (copperCost > 0) {
            copperCoins ++;
            copperCost--;
            if (copperCoins > 64) {
                copperCoins -= 60;
                silverCoins += 6;
            }
            if (silverCoins > 64) {
                silverCoins -= 60;
                goldenCoins += 6;
            }
            if (goldenCoins > 64) {
                goldenCoins -= 60;
                platinumCoins += 6;
            }
        }
        if(platinumCoins > 0) recipe.addIngredient(platinumCoin.getItem(platinumCoins));
        if(goldenCoins > 0) recipe.addIngredient(goldenCoin.getItem(goldenCoins));
        if(recipe.getIngredients().size() < 2 && silverCoins > 0) recipe.addIngredient(silverCoin.getItem(silverCoins));
        if(recipe.getIngredients().size() < 2 && copperCoins > 0) recipe.addIngredient(copperCoin.getItem(copperCoins));
    }
}

