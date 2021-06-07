package com.gabrielmartinmoran.ourcraft.ourcraft_2.economy;


import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.CopperCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.GoldenCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.PlatinumCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.SilverCoin;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MaterialPrice {

    private final int DEFAULT_MAX_USES = 1;
    public Villager.Profession villagerProfession;
    private Material material;
    private int copperUnitSellPrice;
    public boolean allowSell;
    public boolean allowBuy;
    private Random rand;
    private static final CopperCoin copperCoin = new CopperCoin();
    private static final SilverCoin silverCoin = new SilverCoin();
    private static final GoldenCoin goldenCoin = new GoldenCoin();
    private static final PlatinumCoin platinumCoin = new PlatinumCoin();

    // Encarece el costo cuanto menor sea el nivel del aldeano, mas barato sera el precio por item (jugador vended mas barato)
    private static List<Double> sellPriceModifiersByVillagerLevel = Arrays.asList(
            0.7d, // Aldeano nivel 1
            0.8d, // Aldeano nivel 2
            0.9d, // Aldeano nivel 3
            1.0d, // Aldeano nivel 4
            1.1d  // Aldeano nivel 5
    );
    // Encarece el costo cuanto menor sea el nivel del aldeano, mas caro sera el precio por item (jugador compra mas caro)
    private static List<Double> buyPriceModifiersByVillagerLevel = Arrays.asList(
            1.3d, // Aldeano nivel 1
            1.2d, // Aldeano nivel 2
            1.1d, // Aldeano nivel 3
            1.0d, // Aldeano nivel 4
            0.9d  // Aldeano nivel 5
    );

    /**
     * @param material: material identificador del item
     * @param copperUnitSellPrice: indica el precio estandar al que el jugador vende el objeto
     */
    public MaterialPrice(Villager.Profession villagerProfession, Material material, int copperUnitSellPrice, boolean allowSell, boolean allowBuy) {
        this.rand = new Random();
        this.villagerProfession = villagerProfession;
        this.material = material;
        this.copperUnitSellPrice = copperUnitSellPrice;
        this.allowSell = allowSell;
        this.allowBuy = allowBuy;
    }

    public MerchantRecipe getBuyRecipe(int villagerLevel) {
        int tradeAmount = this.generateRecipeAmount();
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(this.material, tradeAmount), DEFAULT_MAX_USES);
        this.completeRecipeBuyCosts(recipe, tradeAmount, villagerLevel);
        return recipe;
    }

    public MerchantRecipe getSellRecipe(int villagerLevel) {
        int tradeAmount = this.generateRecipeAmount();
        ItemStack tradeResult = this.generateRecipeSellProfit(tradeAmount, villagerLevel);
        MerchantRecipe recipe = new MerchantRecipe(tradeResult, DEFAULT_MAX_USES);
        recipe.addIngredient(new ItemStack(this.material, tradeAmount));
        return recipe;
    }

    private MerchantRecipe getRecipe(int villagerLevel, boolean isSelling) {
        int tradeAmount = this.generateRecipeAmount();
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(this.material), tradeAmount);
        this.completeRecipeBuyCosts(recipe, tradeAmount, villagerLevel);
        return recipe;
    }

    private int generateRecipeAmount() {
        ItemStack stack = new ItemStack(this.material);
        int maxStackSize = stack.getMaxStackSize();
        if (maxStackSize == 1) return maxStackSize;
        List<Integer> stackDividers = Arrays.asList(1, 2, 4, 8);
        int stackDivider = stackDividers.get(this.rand.nextInt(stackDividers.size()));
        return maxStackSize / stackDivider;
    }

    private void completeRecipeBuyCosts(MerchantRecipe recipe, int amount, int villagerLevel) {
        long copperCost = (long) (amount * copperUnitSellPrice * buyPriceModifiersByVillagerLevel.get(villagerLevel - 1));
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

    private ItemStack generateRecipeSellProfit(int amount, int villagerLevel) {
        long copperCost = (long) (amount * copperUnitSellPrice * sellPriceModifiersByVillagerLevel.get(villagerLevel - 1));
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
        if(platinumCoins > 0) return platinumCoin.getItem(platinumCoins + Math.round(goldenCoins / 10));
        if(goldenCoins > 0) return goldenCoin.getItem(goldenCoins + Math.round(silverCoins / 10));
        if(silverCoins > 0) return silverCoin.getItem(silverCoins + Math.round(copperCoins / 10));
        return copperCoin.getItem(copperCoins);
    }
}
