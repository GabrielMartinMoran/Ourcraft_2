package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.RecipesLocker;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration.HydrationDecreaseEvents;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.AttributeLevelingHandler;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {

    private RecipesLocker recipesLocker;
    private AttributeLevelingHandler attributeLevelingHandler;

    public CraftingListener() {
        this.recipesLocker = new RecipesLocker();
        this.attributeLevelingHandler = new AttributeLevelingHandler();
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (event.getRecipe() == null) return;
        HumanEntity hw = event.getView().getPlayer();
        PlayerData playerData = PlayerDataProvider.get(hw.getName());
        if (!this.recipesLocker.isRecipeAvaliable(playerData, event.getRecipe())) {
            event.getInventory().setResult(null);
            return;
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack result = event.getRecipe().getResult();
        HumanEntity player = event.getWhoClicked();
        // Sed
        PlayerData playerData = PlayerDataProvider.get(player.getName());
        if (!this.recipesLocker.isRecipeAvaliable(playerData, event.getRecipe())) {
            player.sendMessage("" + ChatColor.RED + "Tu nivel de " + ChatColor.LIGHT_PURPLE + this.recipesLocker.getRecipeNeededAttribute(event.getRecipe()).getDisplayName() + ChatColor.RED + " no es lo suficientemente alto como para craftear este ítem!");
            event.setCancelled(true);
            return;
        }
        int craftedAmount = this.getCraftedAmount(event);
        this.attributeLevelingHandler.onItemCrafted(event.getRecipe().getResult(), (Player) player, craftedAmount);
        if (player.getGameMode() != GameMode.CREATIVE)
            playerData.getHydrationManager().consumeHydration(HydrationDecreaseEvents.CRAFT);
    }

    private int getCraftedAmount(CraftItemEvent event) {
        ItemStack craftedItem = event.getInventory().getResult(); //Get result of recipe
        Inventory Inventory = event.getInventory(); //Get crafting inventory
        ClickType clickType = event.getClick();
        int realAmount = craftedItem.getAmount();
        if (clickType.isShiftClick()) {
            int lowerAmount = craftedItem.getMaxStackSize() + 1000; //Set lower at recipe result max stack size + 1000 (or just highter max stacksize of reciped item)
            for (ItemStack actualItem : Inventory.getContents()) //For each item in crafting inventory
            {
                if (!actualItem.getType().isAir() && lowerAmount > actualItem.getAmount() && !actualItem.getType().equals(craftedItem.getType())) //if slot is not air && lowerAmount is highter than this slot amount && it's not the recipe amount
                    lowerAmount = actualItem.getAmount(); //Set new lower amount
            }
            //Calculate the final amount : lowerAmount * craftedItem.getAmount
            realAmount = lowerAmount * craftedItem.getAmount();
        }
        return realAmount;
    }
}
