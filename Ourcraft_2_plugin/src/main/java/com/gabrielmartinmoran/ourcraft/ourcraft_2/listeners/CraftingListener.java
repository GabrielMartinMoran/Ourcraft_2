package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.RecipesLocker;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.TippedArrowUnlock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration.HydrationDecreaseEvents;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.AttributeLevelingHandler;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.TippedArrowsHelper;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;

public class CraftingListener implements Listener {

    private RecipesLocker recipesLocker;
    private AttributeLevelingHandler attributeLevelingHandler;

    public CraftingListener() {
        this.recipesLocker = new RecipesLocker();
        this.attributeLevelingHandler = new AttributeLevelingHandler();
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (event.getRecipe() == null) {
            this.handleTippedArrowCrafting(event);
            return;
        }
        if (!this.isValidRecipe(event)) {
            event.getInventory().setResult(null);
            return;
        }
        HumanEntity hw = event.getView().getPlayer();
        PlayerData playerData = PlayerDataProvider.get(hw.getName());
        if (!this.recipesLocker.isRecipeAvailable(playerData, event.getRecipe())) {
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
        if (!this.recipesLocker.isRecipeAvailable(playerData, event.getRecipe())) {
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

    private void handleTippedArrowCrafting(PrepareItemCraftEvent event) {
        // Checkeamos si son flechas especiales
        ItemStack[] matrix = event.getInventory().getMatrix();
        if (matrix.length < 9) return;
        int potionIndex = 4;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null) return;
            if (matrix[i].getType() == Material.ARROW && i != potionIndex) continue;
            if (matrix[i].getType() == Material.POTION && i == potionIndex) continue;
            return;
        }
        PotionMeta potionMeta = (PotionMeta) matrix[potionIndex].getItemMeta();
        Player player = (Player) event.getView().getPlayer();
        for (TippedArrowUnlock tippedArrowUnlock : Config.TIPPED_ARROWS_UNLOCKS) {
            if (PlayerDataProvider.get(player).getAttributeLevel(PlayerAttributes.RANGED) < tippedArrowUnlock.unlockLevel)
                continue;
            PotionData potionData = potionMeta.getBasePotionData();
            int potionAmplifier = potionData.isUpgraded() ? 1 : 0;
            int potionDuration = potionData.isExtended() ? Config.TICKS_PER_SECOND * 600 : Config.TICKS_PER_SECOND * 300;
            if (potionData.getType().getEffectType() == tippedArrowUnlock.effect && potionAmplifier == tippedArrowUnlock.amplifier) {
                event.getInventory().setResult(TippedArrowsHelper.getArrow(tippedArrowUnlock.effect, tippedArrowUnlock.amplifier, potionDuration, 8));
                //player.updateInventory();
                return;
            }
        }
    }

    private boolean isValidRecipe(PrepareItemCraftEvent event) {
        return true;
    }
}
